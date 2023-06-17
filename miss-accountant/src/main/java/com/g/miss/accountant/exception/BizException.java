package com.g.miss.accountant.exception;

import com.g.miss.accountant.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.g.miss.accountant.enums.StatusCodeEnum.FAIL;

/**
 * @author G
 * @description 錯誤類
 * @date 2023/6/17 5:00 PM
 */

@Getter
@AllArgsConstructor
public class BizException extends RuntimeException {

    /**
     * 錯誤代碼
     */
    private Integer code = FAIL.getCode();

    /**
     * 錯誤訊息
     */
    private final String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
    }
}
