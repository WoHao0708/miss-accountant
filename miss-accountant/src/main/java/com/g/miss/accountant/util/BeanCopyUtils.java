package com.g.miss.accountant.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author G
 * @description 複製物件或集合
 * @date 2023/6/19 4:19 PM
 */
public class BeanCopyUtils {
    /**
     * 複製物件
     *
     * @param source 來源
     * @param target 目標
     * @return {@link T} 物件
     */
    public static <T> T copyObject(Object source, Class<T> target) {
        T temp = null;
        try {
            temp = target.newInstance();
            if (null != source) {
                org.springframework.beans.BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 複製集合
     *
     * @param source 來源
     * @param target 目標
     * @return {@link List <T>} 集合
     */
    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && source.size() > 0) {
            for (Object obj : source) {
                list.add(BeanCopyUtils.copyObject(obj, target));
            }
        }
        return list;
    }

}
