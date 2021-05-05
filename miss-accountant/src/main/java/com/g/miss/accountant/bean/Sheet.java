package com.g.miss.accountant.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sheet {

    private String fromName;
    private String toName;
    private int amount;

    public Sheet(String fromName, String toName, int amount){
        this.fromName = fromName;
        this.toName = toName;
        this.amount = amount;
    }

    public Sheet(){}
}
