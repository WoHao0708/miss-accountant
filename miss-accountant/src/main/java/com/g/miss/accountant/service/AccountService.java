package com.g.miss.accountant.service;

import com.g.miss.accountant.Template.AdvanceTemplate;
import com.g.miss.accountant.bean.Account;
import com.g.miss.accountant.constants.Constants;
import com.g.miss.accountant.dao.AccountDao;
import com.g.miss.accountant.enums.TypeEnum;
import com.g.miss.accountant.Template.AccountantTemplate;
import com.g.miss.accountant.util.JsonUtils;
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

    public String getGroupUserExcept(String groupId, String userId, String name) {
        if (groupId.isEmpty() || userId.isEmpty()) return null;
        setAccount(groupId, userId, name);
        List<Account> accountList = accountDao.findAccountInfoByGroupIdAndUserIdIsNot(groupId, userId);

        return JsonUtils.toJson(accountList);
    }

    public List<Account> getGroupAllUser(String groupId, String userId) {
        if (groupId.isEmpty() || userId.isEmpty()) return null;
        List<Account> accountList = accountDao.findAccountInfoByGroupId(groupId);

        return accountList;
    }

    private void setAccount(String groupId, String userId, String name) {
        Account account = accountDao.findAccountInfoByUserIdAndGroupId(userId, groupId);

        if (account == null) account = new Account(userId, groupId);
        account.updateInfo(name);

        accountDao.save(account);
    }
}
