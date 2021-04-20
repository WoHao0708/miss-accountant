package com.g.miss.accountant.service;

import com.g.miss.accountant.bean.PublicFund;
import com.g.miss.accountant.dao.PublicFundDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PublicFundService {

    @Autowired
    PublicFundDao publicFundDao;

    public String addOrUpdatePublicFund(String groupId, int amount) {
        PublicFund publicFund = publicFundDao.findByGroupId(groupId);

        if (publicFund == null) publicFund = new PublicFund(groupId, amount);
        int total = publicFund.getAmount() + amount;
        publicFund.setAmount(total);
        publicFundDao.save(publicFund);

        return "公款: " + total;
    }
}
