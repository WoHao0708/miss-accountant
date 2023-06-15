package com.g.miss.accountant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

/**
 * @author G
 * @description 成功新增債權訊息, 隨機結果
 * @date 2023/6/8 12:19 PM
 */
@Getter
@AllArgsConstructor
public enum SuccessMsgEnum {
    Success(1, "Success~"),
    Ok(2, "Ok~"),
    Beggar(3, "乞丐喔");

    private final int id;
    private final String msg;

    /**
     * 取得隨機結果訊息
     *
     * @return 結果訊息
     */
    public static String getRandomMsg() {
        Random r = new Random();
        int n = r.nextInt(3) + 1;

        for (SuccessMsgEnum smEnum : SuccessMsgEnum.values())
            if (smEnum.id == n) return smEnum.msg;
        return Success.msg;
    }
}
