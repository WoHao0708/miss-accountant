package com.g.miss.accountant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeEnum {

    Amount(1, "Amount", "記帳"),
    Advance(2, "Advance", "分帳");

    private int id;
    private String en;
    private String cn;
}
