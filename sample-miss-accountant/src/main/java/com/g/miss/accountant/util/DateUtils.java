package com.g.miss.accountant.util;

import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DateUtils.
 *
 */
public final class DateUtils {

	/** 日期格式化：yyyyMMdd */
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	/** 日期格式化：yyyy-MM-dd HH:mm:ss */
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/** 日期格式化：yyyy-MM-dd'T'HH:mm:ss */
	public static final String DATE_FORMAT_YYYY_MM_T_DD_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
    /** 日期格式化：yyyy-MM-dd HH:mm:ss */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	/**
	 * format.
	 *
	 * @param date
	 * @return
	 */
	public static String format(final Date date) {

		return format(date, DATE_FORMAT_YYYY_MM_DD_HH_MM);
	}

	/**
	 * format.
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(final Date date, final String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * format.
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(final String date, final String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(parse(date, format));
	}

	/**
	 * 日期格式時區转换
	 */
	public static String format(Date date, String format, TimeZone timeZone) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(timeZone);
		return sdf.format(date);
	}

	/**
	 * parse.
	 *
	 * @param date
	 * @return
	 */
	public static Date parse(final String date) {
		return parse(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * parse.
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parse(final String date, final String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("[DateUtil#parse(final String date, final String format)]:" + e.getMessage());
		}
	}

	/**
	 * 判断当前时间是否在开始时间与结束时间之间（不包含等于）
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean isBetween(String beginTime, String endTime) {
		return isBetween(beginTime, endTime, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 判断当前时间是否在开始时间与结束时间之间（不包含等于）
	 * @param beginTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static boolean isBetween(String beginTime, String endTime, String format) {
		Date now = new Date();
		boolean before = now.before(parse(endTime, format));
		boolean after = now.after(parse(beginTime, format));
		return before && after;
	}

	/**
	 * 判斷时间是否符合格式.
	 *
	 * @param dateTime
	 * @param dateFormatePattern
	 * @return
	 */
	public static boolean checkDateFormate(String dateTime, String dateFormatePattern) {

		try{
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormatePattern);
			sdf.setLenient(false);
			Date date = sdf.parse(dateTime);
			return (date != null);
		} catch(ParseException ex) {
			return false;
		}
	}

	/**
	 * 根据开始时间和结束时间返回时间段内的时间集合
	 *
	 * @param startDate
	 * @param endDate
	 * @param pattern 建议使用yyyyMMdd
	 * @return Set
	 */
	public static List<String> getDatesBetweenTwoDate(String startDate, String endDate, String pattern) {

		List<String> dateList = new LinkedList<String>();
		dateList.add(startDate);

		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(parse(startDate, pattern));

		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(parse(endDate, pattern));

		while (calBegin.before(calEnd)) {
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			dateList.add(format(calBegin.getTime(), pattern));
		}

		return dateList;
	}

	/**
	 * 根据开始时间和结束时间返回时间段内的天數總計
	 *
	 * @param startDate
	 * @param endDate
	 * @param pattern startDate and endDate pattern
	 * @return Integer
	 */
	public static Integer getDayBetweenTwoDate(String startDate, String endDate, String pattern) {

		List<String> dateList = getDatesBetweenTwoDate(startDate, endDate, pattern);
		Integer day = 0;

		if (!CollectionUtils.isEmpty(dateList)) {
			day = dateList.size();
		}

		return day;
	}

	/**
	 * getCnDate.
	 *
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date getCnDate(String dateStr, String format) {

		return getDateByFormatFieldAndIntervalHours(parse(dateStr, format), Calendar.HOUR_OF_DAY, 12);
	}

	/**
	 * getUsDate.
	 *
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date getUsDate(String dateStr, String format) {

		return getDateByFormatFieldAndIntervalHours(parse(dateStr, format), Calendar.HOUR_OF_DAY, -12);
	}

	/**
	 * getDateByFormatFieldAndIntervalHours.
	 *
	 * @param date
	 * @param formatField
	 * @param intervalHours
	 * @return
	 */
	public static Date getDateByFormatFieldAndIntervalHours(Date date, int formatField, int intervalHours) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(formatField, intervalHours);

		return calendar.getTime();
	}

	/**
	 * 获取固定间隔时刻集合.
	 *
	 * @param startTime
	 * @param endTime
	 * @param interval
	 * @return
	 */
	public static List<String> getIntervalTimeList(String startTime, String endTime, int interval) {

		Date startDate = parse(startTime, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
		Date endDate = parse(endTime, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);

		List<String> list = new ArrayList<String>();
		while (startDate.getTime() <= endDate.getTime()) {
			list.add(format(startDate, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.MINUTE, interval);
			if (calendar.getTime().getTime() > endDate.getTime()) {
				if (!startDate.equals(endDate)) {
					list.add(format(endDate, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
				}
				startDate = calendar.getTime();
			} else {
				startDate = calendar.getTime();
			}

		}
		return list;
	}

	public static Date getDateFromTpDateType(String dt) {
		Date resultDate = null;
		if(StringUtils.isNotEmpty(dt) && dt.length() >= 19) {
			String mainDate = StringUtils.substring(dt, 0, 20);
			resultDate = parse(mainDate, DATE_FORMAT_YYYY_MM_T_DD_HH_MM_SS);
		}
		return resultDate;
	}

	/**
	 * 时间转为时间对象
	 * @param date 时间字符串
	 * @param pattern 格式
	 * @return
	 */
	public static Date parseTimezoneDate(Date date, String pattern, String time_zone) {
		return parse(formatTimezoneDate(date, pattern, time_zone));
	}

	/**
	 * 将时间字符串转为指定时区的时间对象
	 * @param date 时间字符串
	 * @param pattern 格式
	 * @param timeZoneId 时区
	 * @return
	 */
	public static Date parseTimezoneDate(String date, String pattern, String timeZoneId) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			if (null != timeZoneId) {
				sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			}
			return sdf.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatTimezoneDate(String dateStr, String pattern, String timeZoneId) {
		return formatTimezoneDate(parse(dateStr, pattern), pattern, timeZoneId);
	}

	/**
	 * 格式化时间
	 * @param date 时间
	 * @param pattern 格式
	 * @param timeZoneId 时区
	 * @return
	 */
	public static String formatTimezoneDate(Date date, String pattern, String timeZoneId) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			if (null != timeZoneId) {
				sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			}
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 取得相減後的天數.
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long timeDifference(String startTime, String endTime) {

		Date startDate = parse(startTime, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
		Date endDate = parse(endTime, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);

		long betweenDay = endDate.getTime() - startDate.getTime();
		long day = betweenDay / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 計算時間加減後的日期.
	 *
	 * @param date
	 * @param day
	 * @return
	 */
	public static String addAndSubtractionDate(Date date, int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);

		Date afterDate = calendar.getTime();
		return format(afterDate);
	}

	/**
	 * 取得Date的unix timestamp
	 */
	public static Long getUnixTimestamp(Date date) {

		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
		Long unix = null;

		try {
			unix = df.parse(date.toString()).getTime();
		} catch (Exception e) {
			return null;
		}

		return unix;
	}

	/**
	 * 取得Date的unix timestamp
	 */
	public static Long getUnixTimestamp(String data) {

		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
		Long unix = null;

		try {
			unix = df.parse(data).getTime();
		} catch (Exception e) {
			return null;
		}

		return unix;
	}
}
