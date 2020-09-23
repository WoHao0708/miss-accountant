package com.g.miss.accountant.service;

import com.g.miss.accountant.bean.AccountInfo;
import com.g.miss.accountant.constants.Constants;
import com.g.miss.accountant.dao.AccountInfoDao;
import com.g.miss.accountant.enums.TypeEnum;
import com.g.miss.accountant.Template.AccountantTemplate;
import com.linecorp.bot.model.message.FlexMessage;
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

    public FlexMessage checkGroupAmount(String groupId) {

        List<AccountInfo> list = accountInfoDao.findAccountInfoByGroupId(groupId);

        return new AccountantTemplate().get(list);
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

    public String switchIsAdvance(String userId, String groupId, String name) {

        AccountInfo accountInfo = accountInfoDao.findAccountInfoByUserIdAndGroupId(userId, groupId);

        if (accountInfo == null) accountInfo = new AccountInfo(userId, groupId);

        String result = accountInfo.switchIsAdvance();
        accountInfo.updateInfo(name);
        accountInfoDao.save(accountInfo);

        return name + result;
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
