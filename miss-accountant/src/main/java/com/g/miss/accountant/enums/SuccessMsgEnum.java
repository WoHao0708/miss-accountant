package com.g.miss.accountant.enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum SuccessMsgEnum {
    Success(1, "Success~"),
    Ok(2, "Ok~"),
    Nice(3, "Nice~"),
    Good(4, "Good~"),
    Beggar(5, "乞丐喔");

    private int id;
    private String msg;

    SuccessMsgEnum(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public static String getRandomMsg() {
        Random r = new Random();
        int n = r.nextInt(5) + 1;

        for (SuccessMsgEnum smEnum : SuccessMsgEnum.values())
            if (smEnum.id == n) return smEnum.msg;
        return Success.msg;
    }
}
