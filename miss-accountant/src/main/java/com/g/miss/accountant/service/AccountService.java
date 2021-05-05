package com.g.miss.accountant.service;

import com.g.miss.accountant.Template.SheetTemplate;
import com.g.miss.accountant.bean.Account;
import com.g.miss.accountant.bean.Debt;
import com.g.miss.accountant.bean.Sheet;
import com.g.miss.accountant.dao.AccountDao;
import com.g.miss.accountant.dao.DebtDao;
import com.g.miss.accountant.Template.AccountTemplate;
import com.g.miss.accountant.util.JsonUtils;
import com.linecorp.bot.model.message.FlexMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    AccountDao accountDao;
    @Autowired
    DebtDao debtDao;

    public FlexMessage checkGroupAmount(String groupId) {
        List<Account> accountList = getGroupAmount(groupId);
        if (accountList.size() == 0) return null;
        return new AccountTemplate().get(accountList);
    }

    // FlexMessage
    public FlexMessage allotMoney(String groupId) { // 分帳演算法
        List<Account> accountList = getGroupAmount(groupId);
        List<Sheet> sheets = new ArrayList<>();
        while (accountList.size() != 0) {
            accountList = accountList.stream().sorted(Comparator.comparing(Account::getAmount).reversed()).collect(Collectors.toList());

            Account head = accountList.get(0);
            Account tail = accountList.get(accountList.size() - 1);
            int amount = 0;

            if (head.getAmount() == -tail.getAmount()) {
                accountList.remove(tail);
                accountList.remove(head);
                amount = head.getAmount();
            } else if (head.getAmount() > -tail.getAmount()) {
                head.setAmount(head.getAmount() + tail.getAmount());
                accountList.remove(tail);
                amount = -tail.getAmount();
            } else {
                tail.setAmount(tail.getAmount() + head.getAmount());
                accountList.remove(head);
                amount = head.getAmount();
            }

            Sheet sheet = new Sheet(tail.getName(), head.getName(), amount);
            sheets.add(sheet);
        }

        if (sheets.size() == 0) return null;

        sheets = sheets.stream().sorted(Comparator.comparing(Sheet::getFromName)).collect(Collectors.toList());
        return new SheetTemplate().get(sheets);
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

    private List<Account> getGroupAmount(String groupId) {

        List<Account> accountList = accountDao.findAccountInfoByGroupId(groupId);
        List<Debt> debtList = debtDao.findDebtByGroupIdAndIsDelete(groupId, 0);

        for (Debt debt : debtList) {
            Account account = accountList.stream()
                    .filter(a -> debt.getUserId().equals(a.getUserId()))
                    .findAny().orElse(null);
            account.addAmount(-debt.getAmount());
            account = accountList.stream()
                    .filter(a -> debt.getCreditor().equals(a.getUserId()))
                    .findAny().orElse(null);
            account.addAmount(debt.getAmount());
        }

        // 先排除0的帳號
        accountList = accountList.stream()
                .filter(a -> a.getAmount() != 0)
                .collect(Collectors.toList());

        return accountList;
    }
}
