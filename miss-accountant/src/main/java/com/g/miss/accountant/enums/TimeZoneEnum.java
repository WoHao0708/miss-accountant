package com.g.miss.accountant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author G
 * @description 時區列舉
 * @date 2023/6/8 11:07 AM
 */
@Getter
@AllArgsConstructor
public enum TimeZoneEnum {

    SHANGHAI("Asia/Shanghai", "上海");

    /**
     * 區域
     */
    private final String zone;

    /**
     * 描述
     */
    private final String desc;
}
