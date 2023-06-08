package com.g.miss.accountant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g.miss.accountant.entity.Account;
import com.linecorp.bot.model.message.FlexMessage;

/**
 * @author G
 * @description 帳號
 * @date 2023/6/8 12:17 PM
 */
public interface AccountService extends IService<Account> {

    /**
     * 更新帳號資訊
     *
     * @param groupId 群組id
     * @param userId  使用者id
     * @param name    名稱
     * @return 成功或失敗
     */
    boolean updateInfo(String groupId, String userId, String name);


    /**
     * 取得群組內自己以外的其他人帳號
     *
     * @param groupId 群組id
     * @param userId  使用者id
     * @param name    名稱
     * @return 帳號列表
     */
    String listGroupUserExceptItself(String groupId, String userId, String name);

    /**
     * 取得分帳結果訊息
     *
     * @param groupId 群組id
     * @return 結果
     */
    FlexMessage getAllotFlexMessage(String groupId);

    /**
     * 取得群組總計訊息
     *
     * @param groupId 群組id
     * @return 結果
     */
    FlexMessage getGroupAmountFlexMessage(String groupId);


}
