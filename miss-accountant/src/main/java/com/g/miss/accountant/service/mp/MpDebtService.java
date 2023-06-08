package com.g.miss.accountant.service.mp;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g.miss.accountant.entity.Debt;

/**
 * @author G
 * @description 債權
 * @date 2023/6/8 12:34 PM
 */
public interface MpDebtService extends IService<Debt> {

    /**
     * 刪除群組所有債權
     *
     * @param groupId 群組id
     * @return 結果
     */
    String deleteGroupDebt(String groupId);

}
