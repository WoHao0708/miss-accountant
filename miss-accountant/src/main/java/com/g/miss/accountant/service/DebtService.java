package com.g.miss.accountant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g.miss.accountant.entity.Debt;

/**
 * @author G
 * @description 債權
 * @date 2023/6/8 12:34 PM
 */
public interface DebtService extends IService<Debt> {

    /**
     * 刪除群組所有債權
     *
     * @param groupId 群組id
     * @return 結果
     */
    String deleteGroupDebt(String groupId);

    /**
     * 批次新增債權
     *
     * @param userIds  用戶id列表
     * @param creditor 債權人
     * @param groupId  群組id
     * @param amount   金額
     * @param note     筆記
     * @return 結果
     */
    String addDebt(String[] userIds, String creditor, String groupId, int amount, String note);

    /**
     * 取得債權列表
     *
     * @param groupId 群組id
     * @param userId  使用者id
     * @return 結果
     */
    String listDebt(String groupId, String userId);

    /**
     * 刪除債權
     *
     * @param debtId 債權id
     * @return 結果
     */
    String deleteDebt(int debtId);


}
