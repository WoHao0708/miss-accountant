package com.g.miss.accountant.vo;

import lombok.Data;

import static com.g.miss.accountant.enums.StatusCodeEnum.FAIL;
import static com.g.miss.accountant.enums.StatusCodeEnum.SUCCESS;


/**
 * @author G
 * @description 接口返回結果
 * @date 2023/6/9 6:11 PM
 */
@Data
public class Result<T> {

    /**
     * 返回狀態
     */
    private boolean flag;

    /**
     * 返回代碼
     */
    private Integer code;

    /**
     * 返回訊息
     */
    private String message;

    /**
     * 返回數據
     */
    private T date;

    public static <T> Result<T> ok() {
        return restResult(true, null, SUCCESS.getCode(), SUCCESS.getDesc());
    }

    public static <T> Result<T> ok(T data) {
        return restResult(true, data, SUCCESS.getCode(), SUCCESS.getDesc());
    }

    public static <T> Result<T> ok(String message) {
        return restResult(true, null, SUCCESS.getCode(), message);
    }

    public static <T> Result<T> ok(boolean message) {
        return restResult(true, null, SUCCESS.getCode(), String.valueOf(message));
    }

    public static <T> Result<T> fail() {
        return restResult(false, null, FAIL.getCode(), FAIL.getDesc());
    }

    private static <T> Result<T> restResult(Boolean flag, T data, Integer code, String message) {
        Result<T> apiResult = new Result<>();
        apiResult.setFlag(flag);
        apiResult.setDate(data);
        apiResult.setCode(code);
        apiResult.setMessage(message);
        return apiResult;
    }
}
