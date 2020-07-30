package com.example.bot.spring.service;

import com.example.bot.spring.bean.Record;
import com.example.bot.spring.dao.AccountInfoDao;
import com.example.bot.spring.dao.RecordDao;
import com.example.bot.spring.util.DateUtils;
import com.example.bot.spring.util.StringUtils;
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

    public String getRecordBy(String userId, String groupId, int count) {
        if (count == 0) count = 10;

        Pageable top = PageRequest.of(0, count);
        List<Record> list = recordDao.findByUserIdAndGroupIdOrderByIdDesc(userId, groupId, top);
        StringBuilder result = new StringBuilder();

        for (Record item : list) {
            String str;
            if (item.getAmount() == 0) str = "=0";
            else if (item.getAmount() > 0) str = "+" + item.getAmount();
            else str = "" + item.getAmount();
            result.append(DateUtils.format(item.getCreatedTime())).append(" ").append(str).append("\n");
        }

        return result.toString().substring(0, result.length() - 1);
    }
}
