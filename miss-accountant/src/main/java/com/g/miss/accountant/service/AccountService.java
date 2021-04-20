package com.g.miss.accountant.service;

import com.g.miss.accountant.Template.AdvanceTemplate;
import com.g.miss.accountant.bean.Account;
import com.g.miss.accountant.constants.Constants;
import com.g.miss.accountant.dao.AccountDao;
import com.g.miss.accountant.enums.TypeEnum;
import com.g.miss.accountant.Template.AccountantTemplate;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountDao accountDao;
    @Autowired
    RecordService recordService;

    public int AddOrUpdateAmount(String userId, String groupId, String name, int amount) {

        Account account = accountDao.findAccountInfoByUserIdAndGroupId(userId, groupId);

        if (account != null)
            account.setAmount(account.getAmount() + amount);
        else
            account = new Account(userId, groupId, amount, TypeEnum.Amount.getId());

        account.updateInfo(name);
        accountDao.save(account);

        if (amount != 0) recordService.addRecord(userId, groupId, amount); // 金額 +-0 不做紀錄

        return account.getAmount();
    }

    public int AddOrUpdateAdvance(String userId, String groupId, String name, int advance) {

        Account account = accountDao.findAccountInfoByUserIdAndGroupId(userId, groupId);

        if (account != null) {
            account.setAdvance(account.getAdvance() + advance);
            account.setIsAdvance(1);
        } else {
            account = new Account(userId, groupId, advance, TypeEnum.Advance.getId());
        }


        account.updateInfo(name);
        accountDao.save(account);

        return account.getAdvance();
    }

    public void setAmountToZero(String userId, String groupId, String name) {

        Account account = accountDao.findAccountInfoByUserIdAndGroupId(userId, groupId);

        if (account == null)
            account = new Account(userId, groupId, 0, TypeEnum.Amount.getId());

        account.setAmount(0);
        account.updateInfo(name);
        accountDao.save(account);

        recordService.addRecord(userId, groupId, 0);
    }

    public void setAdvanceToZero(String userId, String groupId, String name) {

        Account account = accountDao.findAccountInfoByUserIdAndGroupId(userId, groupId);

        if (account == null)
            account = new Account(userId, groupId, 0, TypeEnum.Advance.getId());

        account.setAdvance(0);
        account.updateInfo(name);
        accountDao.save(account);
    }

    public Message setGroupAdvanceToZero(String groupId) {
        this.setAllUserIsAdvance(groupId, 0);

        List<Account> accountList = accountDao.findAccountInfoByGroupId(groupId);
        StringBuilder result = new StringBuilder();

        for (Account account : accountList) {
            account.setAdvance(0);
            account.updateInfo(account.getName());
            accountDao.save(account);

            result.append(account.getName()).append(", ");
        }
        if (accountList.size() > 0) result.append(Constants.ADVANCE_SUCCESS);
        return new TextMessage(result.toString());
    }

    public FlexMessage checkGroupAmount(String groupId) {

        List<Account> list = accountDao.findAccountInfoByGroupId(groupId);

        return new AccountantTemplate().get(list);
    }

    public Message checkGroupAdvance(String groupId) {
        return checkGroupAdvance(groupId, null);
    }

    public Message checkGroupAdvance(String groupId, Integer number) { // number 外人分帳

        List<Account> list = accountDao.findAccountInfoByGroupIdAndIsAdvance(groupId, 1);

        if (number == null) number = list.size();

        if (list.size() > 0) return new AdvanceTemplate().get(list, number);
        else return new TextMessage(Constants.ADVANCE_ERROR_MESSAGE);
    }

    public String switchIsAdvance(String userId, String groupId, String name) {

        Account account = accountDao.findAccountInfoByUserIdAndGroupId(userId, groupId);

        if (account == null) account = new Account(userId, groupId);

        String result = account.switchIsAdvance();
        account.updateInfo(name);
        accountDao.save(account);

        return name + result;
    }

    public String setAllUserIsAdvance(String groupId, int suffix) {

        List<Account> list = accountDao.findAccountInfoByGroupId(groupId);
        StringBuilder result = new StringBuilder();

        if (suffix < 2 && list.size() > 0) {
            for (Account item : list) {
                item.setIsAdvance(suffix);
                accountDao.save(item);
                result.append(item.getName()).append(", ");
            }
            if (suffix == 0) result.append("設定為不分帳");
            if (suffix == 1) result.append("設定為要分帳");
        } else
            result.append("設定失敗");

        return result.toString();
    }

    public List<Account> getGroupUser(String groupId, String userId) {
        if (groupId.isEmpty() || userId.isEmpty()) return null;

        List<Account> accountList = accountDao.findAccountInfoByGroupIdAndUserIdIsNot(groupId, userId);

        return accountList;
    }
}
