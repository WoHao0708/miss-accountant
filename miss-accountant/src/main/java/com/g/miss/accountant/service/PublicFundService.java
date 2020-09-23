package com.g.miss.accountant.service;

import com.g.miss.accountant.Template.RecordTemplate;
import com.g.miss.accountant.bean.PublicFund;
import com.g.miss.accountant.bean.Record;
import com.g.miss.accountant.dao.AccountInfoDao;
import com.g.miss.accountant.dao.PublicFundDao;
import com.g.miss.accountant.dao.RecordDao;
import com.linecorp.bot.model.message.FlexMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


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
