package com.g.miss.accountant.service;

import com.g.miss.accountant.Template.RecordTemplate;
import com.g.miss.accountant.bean.Record;
import com.g.miss.accountant.dao.AccountInfoDao;
import com.g.miss.accountant.dao.RecordDao;
import com.linecorp.bot.model.message.FlexMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RecordService {

    @Autowired
    AccountInfoDao accountInfoDao;
    @Autowired
    RecordDao recordDao;


    public void addRecord(String userId, String groupId, int amount) {
        Record record = new Record(userId, groupId, amount);
        recordDao.save(record);
    }

    public FlexMessage getRecordBy(String userId, String groupId, int count) {
        Pageable top = PageRequest.of(0, count);
        List<Record> list = recordDao.findByUserIdAndGroupIdOrderByIdDesc(userId, groupId, top);
        String name = accountInfoDao.findAccountInfoByUserIdAndGroupId(userId, groupId).getName();

        return new RecordTemplate().get(list, name);
    }
}
