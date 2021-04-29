package com.g.miss.accountant.service;

import com.g.miss.accountant.bean.Account;
import com.g.miss.accountant.bean.Debt;
import com.g.miss.accountant.dao.AccountDao;
import com.g.miss.accountant.dao.DebtDao;
import com.g.miss.accountant.Template.AccountTemplate;
import com.g.miss.accountant.util.JsonUtils;
import com.linecorp.bot.model.message.FlexMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountDao accountDao;
    @Autowired
    DebtDao debtDao;
    @Autowired
    RecordService recordService;

    public FlexMessage checkGroupAmount(String groupId) {

        List<Account> accountList = accountDao.findAccountInfoByGroupId(groupId);
        List<Debt> debtList = debtDao.findDebtByGroupIdAndIsDelete(groupId, 0);

        for (Debt debt: debtList) {
            Account account = accountList.stream()
                    .filter(a -> debt.getUserId().equals(a.getUserId()))
                    .findAny().orElse(null);
            account.addAmount(-debt.getAmount());
            account = accountList.stream()
                    .filter(a -> debt.getCreditor().equals(a.getUserId()))
                    .findAny().orElse(null);
            account.addAmount(debt.getAmount());
        }

        return new AccountTemplate().get(accountList);
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

    public void setAccount(String groupId, String userId, String name) {
        Account account = new Account(userId, groupId);
        account.updateInfo(name);
        accountDao.save(account);
    }
}
