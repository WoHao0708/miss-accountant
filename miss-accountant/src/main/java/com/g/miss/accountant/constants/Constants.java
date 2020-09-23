package com.g.miss.accountant.constants;

public class Constants {

    public static String HELP_MESSAGE = "$$ ：  設定記帳餘額為零\n" +
            "$+n： 增加記帳餘額\n" +
            "$-n： 減少記帳餘額\n" +
            "// ： 設定分帳餘額為零\n" +
            "/$n： 分帳預支\n" +
            "!+n： 增加公款\n" +
            "!-n： 減少公款";

    public static String OTHER_MESSAGE = "$rn： n為顯示筆數, 空值表示預設, 預設為十筆\n" +
            "/an： 設定群組所有人員是否分帳, n為零或一\n" +
            "/dn： 有外人專用的分帳檢查, n = 總分帳人數";

    public static String ADVANCE_ERROR_MESSAGE = "沒人分 是要分三小";

    public static String COLOR_GREEN = "#1DB446";
    public static String COLOR_BLACK = "#555555";
    public static String COLOR_WHITE = "#FFFFFF";
    public static String COLOR_GRAY = "#aaaaaa";
}
