package com.g.miss.accountant.bean;

import lombok.Data;

/**
 * @author G
 * @description 分賬用bean
 * @date 2023/6/8 12:19 PM
 */
@Data
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

    public Sheet(String fromName, String toName, int amount) {
        this.fromName = fromName;
        this.toName = toName;
        this.amount = amount;
    }
}
