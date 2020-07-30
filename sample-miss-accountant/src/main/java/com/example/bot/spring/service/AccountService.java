package com.example.bot.spring.service;

import com.example.bot.spring.bean.AccountInfo;
import com.example.bot.spring.constants.Constants;
import com.example.bot.spring.dao.AccountInfoDao;
import com.example.bot.spring.enums.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountInfoDao accountInfoDao;
    @Autowired
    RecordService recordService;

    public int AddOrUpdateAmount(String userId, String groupId, String name, int amount) {

        AccountInfo accountInfo = accountInfoDao.findAccountInfoByUserIdAndGroupId(userId, groupId);

        if (accountInfo != null)
            accountInfo.setAmount(accountInfo.getAmount() + amount);
        else
            accountInfo = new AccountInfo(userId, groupId, amount, TypeEnum.Amount.getId());

        accountInfo.updateInfo(name);
        accountInfoDao.save(accountInfo);

        if (amount != 0) recordService.addRecord(userId, groupId, amount); // 金額 +-0 不做紀錄

        return accountInfo.getAmount();
    }

    public int AddOrUpdateAdvance(String userId, String groupId, String name, int advance) {

        AccountInfo accountInfo = accountInfoDao.findAccountInfoByUserIdAndGroupId(userId, groupId);

        if (accountInfo != null)
            accountInfo.setAdvance(accountInfo.getAdvance() + advance);
        else
            accountInfo = new AccountInfo(userId, groupId, advance, TypeEnum.Advance.getId());

        accountInfo.updateInfo(name);
        accountInfoDao.save(accountInfo);

        return accountInfo.getAdvance();
    }

    public void setAmountToZero(String userId, String groupId, String name) {

        AccountInfo accountInfo = accountInfoDao.findAccountInfoByUserIdAndGroupId(userId, groupId);

        if (accountInfo == null)
            accountInfo = new AccountInfo(userId, groupId, 0, TypeEnum.Amount.getId());

        accountInfo.setAmount(0);
        accountInfo.updateInfo(name);
        accountInfoDao.save(accountInfo);

        recordService.addRecord(userId, groupId, 0);
    }

    public void setAdvanceToZero(String userId, String groupId, String name) {

        AccountInfo accountInfo = accountInfoDao.findAccountInfoByUserIdAndGroupId(userId, groupId);

        if (accountInfo == null)
            accountInfo = new AccountInfo(userId, groupId, 0, TypeEnum.Advance.getId());

        accountInfo.setAdvance(0);
        accountInfo.updateInfo(name);
        accountInfoDao.save(accountInfo);
    }

    public String checkGroupAmount(String groupId) {

        List<AccountInfo> list = accountInfoDao.findAccountInfoByGroupId(groupId);
        StringBuilder result = new StringBuilder();
        int total = 0;

        for (AccountInfo item : list) {
            result.append(item.getName()).append(": ").append(item.getAmount()).append("\n");
            total += item.getAmount();
        }
        result.append("總計: ").append(total);

        return result.toString();
    }

    public String checkGroupAdvance(String groupId) {

        List<AccountInfo> list = accountInfoDao.findAccountInfoByGroupIdAndIsAdvance(groupId, 1);
        StringBuilder result = new StringBuilder();
        int total = 0;
        int each = 0;

        if (list.size() > 0) {
            for (AccountInfo item : list) {
                result.append(item.getName()).append("預支: ").append(item.getAdvance()).append("\n");
                total += item.getAdvance();
            }

            each = total / list.size();

            result.append("總預支: ").append(total).append(", 人數: ").append(list.size()).append("\n");
            result.append(each).append("/人\n\n");

            for (AccountInfo item : list)
                result.append(item.getName()).append(": ").append(item.getAdvance() - each).append("\n");

        } else
            result.append(Constants.errorMessage);

        return result.toString().substring(0, result.length() - 1);
    }

    public String setIsAdvance(String userId, String groupId, String name, int suffix) {

        AccountInfo accountInfo = accountInfoDao.findAccountInfoByUserIdAndGroupId(userId, groupId);
        StringBuilder result = new StringBuilder();

        if (suffix < 2) {
            if (accountInfo == null) accountInfo = new AccountInfo(userId, groupId);

            accountInfo.setIsAdvance(suffix);
            accountInfo.updateInfo(name);
            accountInfoDao.save(accountInfo);

            result.append(name);
            if (suffix == 0) result.append("設定為不分帳");
            if (suffix == 1) result.append("設定為要分帳");
        } else
            result.append("設定失敗");

        return result.toString();
    }

    public String setAllUserIsAdvance(String groupId, int suffix) {

        List<AccountInfo> list = accountInfoDao.findAccountInfoByGroupId(groupId);
        StringBuilder result = new StringBuilder();

        if (suffix < 2 && list.size() > 0) {
            for (AccountInfo item : list) {
                item.setIsAdvance(suffix);
                accountInfoDao.save(item);
                result.append(item.getName()).append(", ");
            }
            if (suffix == 0) result.append("設定為不分帳");
            if (suffix == 1) result.append("設定為要分帳");
        } else
            result.append("設定失敗");

        return result.toString();
    }
}
