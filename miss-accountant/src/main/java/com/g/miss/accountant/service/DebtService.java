package com.g.miss.accountant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g.miss.accountant.dto.DebtDTO;
import com.g.miss.accountant.entity.Debt;
import com.g.miss.accountant.vo.DebtVO;

import java.util.List;

/**
 * @author G
 * @description 債權
 * @date 2023/6/8 12:34 PM
 */
public interface DebtService extends IService<Debt> {

    /**
     * 取得群組債權列表
     *
     * @param groupId 群組id
     * @return 結果
     */
    List<Debt> listDebtByGroupId(String groupId);

    /**
     * 取得使用者在群組的債
     *
     * @param groupId 群組id
     * @param userId  使用者id
     * @return 結果
     */
    List<Debt> listDebtByGroupIdAndUserId(String groupId, String userId);

    /**
     * 取得使用者在群組的債權
     *
     * @param groupId    群組id
     * @param creditorId 使用者id
     * @return 結果
     */
    List<Debt> listDebtByGroupIdAndCreditorId(String groupId, String creditorId);

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
     * @param debtVO 債權
     * @return 結果
     */
    String addDebt(DebtVO debtVO);

    /**
     * 取得債權列表
     *
     * @param groupId 群組id
     * @param userId  使用者id
     * @return 結果
     */
    List<DebtDTO> listDebt(String groupId, String userId);

    /**
     * 刪除債權
     *
     * @param debtId 債權id
     * @return 結果
     */
    String deleteDebt(int debtId);


}
