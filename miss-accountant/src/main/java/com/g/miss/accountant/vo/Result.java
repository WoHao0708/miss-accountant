package com.g.miss.accountant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import static com.g.miss.accountant.enums.StatusCodeEnum.FAIL;
import static com.g.miss.accountant.enums.StatusCodeEnum.SUCCESS;


/**
 * @author G
 * @description 接口返回結果
 * @date 2023/6/9 6:11 PM
 */
@Data
@ApiModel(description = "接口返回結果")
public class Result<T> {

    /**
     * 返回狀態
     */
    @ApiModelProperty(value = "狀態", dataType = "Boolean")
    private boolean flag;

    /**
     * 返回代碼
     */
    @ApiModelProperty(value = "代碼", dataType = "Integer")
    private Integer code;

    /**
     * 返回訊息
     */
    @ApiModelProperty(value = "訊息", dataType = "String")
    private String message;

    /**
     * 返回數據
     */
    @ApiModelProperty(value = "資料", dataType = "Object")
    private T data;

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

    public static <T> Result<T> fail(String message) {
        return restResult(false, message);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return restResult(false, null, code, message);
    }

    private static <T> Result<T> restResult(Boolean flag, String message) {
        Result<T> apiResult = new Result<>();
        apiResult.setFlag(flag);
        apiResult.setCode(flag ? SUCCESS.getCode() : FAIL.getCode());
        apiResult.setMessage(message);
        return apiResult;
    }

    private static <T> Result<T> restResult(Boolean flag, T data, Integer code, String message) {
        Result<T> apiResult = new Result<>();
        apiResult.setFlag(flag);
        apiResult.setData(data);
        apiResult.setCode(code);
        apiResult.setMessage(message);
        return apiResult;
    }
}
