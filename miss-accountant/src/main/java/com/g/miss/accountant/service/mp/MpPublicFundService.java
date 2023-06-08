package com.g.miss.accountant.service.mp;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g.miss.accountant.entity.PublicFund;

/**
 * @author G
 * @description 公款
 * @date 2023/6/7 4:40 PM
 */
public interface MpPublicFundService extends IService<PublicFund> {

    /**
     * 餘額調整
     *
     * @param groupId 群組id
     * @param amount  調整金額
     * @return 返回調整後餘額
     */
    String updateBalance(String groupId, int amount);
}
