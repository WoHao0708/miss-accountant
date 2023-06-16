package com.g.miss.accountant.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author G
 * @description 分賬用bean
 * @date 2023/6/8 12:19 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sheet {

    /**
     * 收錢人
     */
    private String fromName;
    /**
     * 給錢人
     */
    private String toName;
    /**
     * 金額
     */
    private int amount;
}
