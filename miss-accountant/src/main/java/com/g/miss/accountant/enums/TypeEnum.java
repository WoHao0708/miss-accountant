package com.g.miss.accountant.enums;

import lombok.Getter;

@Getter
public enum TypeEnum {

    Amount(1, "Amount", "記帳"),
    Advance(2, "Advance", "分帳");

    private int id;
    private String en;
    private String cn;

    TypeEnum(int id, String en, String cn) {
        this.id = id;
        this.en = en;
        this.cn = cn;
    }
}
