package com.g.miss.accountant.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g.miss.accountant.dao.PublicFundDao;
import com.g.miss.accountant.entity.PublicFund;
import com.g.miss.accountant.service.PublicFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author G
 * @description 公款
 * @date 2023/6/7 4:40 PM
 */
@Service
public class PublicFundServiceImpl extends ServiceImpl<PublicFundDao, PublicFund> implements PublicFundService {
    @Autowired
    PublicFundDao publicFundDao;

    @Override
    public String updateBalance(String groupId, int amount) {
        PublicFund publicFund = publicFundDao.selectById(groupId);

        if (publicFund == null) publicFund = PublicFund.builder().groupId(groupId).balance(0).build();
        publicFund.addBalance(amount);
        this.saveOrUpdate(publicFund);

        return "公款: " + publicFund.getBalance();
    }
}
