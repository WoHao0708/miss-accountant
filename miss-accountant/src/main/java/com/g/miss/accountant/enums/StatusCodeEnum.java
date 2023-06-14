package com.g.miss.accountant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author G
 * @description 返回狀態列舉
 * @date 2023/6/9 6:18 PM
 */
@Getter
@AllArgsConstructor
public enum StatusCodeEnum {
    /**
     * 成功
     */
    SUCCESS(20000, "操作成功"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(50000, "系统異常"),
    /**
     * 失败
     */
    FAIL(51000, "操作失敗"),
    /**
     * 参数校验失败
     */
    VALID_ERROR(52000, "参數格式不正確"),
    /**
     * 為什麼不選人錯誤
     */
    NONE_CHOICE_ERROR(53000, "為甚麼不選人?");
    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

}
