package com.g.miss.accountant.service.mp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g.miss.accountant.dao.mp.MpPublicFundDao;
import com.g.miss.accountant.entity.PublicFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author G
 * @description 公款
 * @date 2023/6/7 4:40 PM
 */
@Service
public class MpPublicFundServiceImpl extends ServiceImpl<MpPublicFundDao, PublicFund> implements MpPublicFundService {
    @Autowired
    MpPublicFundDao mpPublicFundDao;

    @Override
    public String updateBalance(String groupId, int amount) {
        PublicFund publicFund = mpPublicFundDao.selectById(groupId);

        if (publicFund == null) publicFund = PublicFund.builder().groupId(groupId).balance(0).build();
        publicFund.addBalance(amount);
        this.saveOrUpdate(publicFund);

        return "公款: " + publicFund.getBalance();
    }
}
