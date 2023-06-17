package com.g.miss.accountant.handler;

import com.g.miss.accountant.exception.BizException;
import com.g.miss.accountant.vo.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static com.g.miss.accountant.enums.StatusCodeEnum.SYSTEM_ERROR;
import static com.g.miss.accountant.enums.StatusCodeEnum.VALID_ERROR;

/**
 * @author G
 * @description 異常處理
 * @date 2023/6/17 4:55 PM
 */
@Log4j2
@RestControllerAdvice
public class ControllerAdviceHandler {


    /**
     * 業務邏輯異常處理
     *
     * @param e 異常
     * @return 異常訊息
     */
    @ExceptionHandler(value = BizException.class)
    public Result<?> errorHandler(BizException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 參數驗證異常處理
     *
     * @param e 異常
     * @return 異常訊息
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> errorHandler(MethodArgumentNotValidException e) {
        return Result.fail(VALID_ERROR.getCode(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 系統異常處理
     *
     * @param e 異常
     * @return 異常訊息
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> errorHandler(Exception e) {
        e.printStackTrace();
        return Result.fail(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getDesc());
    }
}
