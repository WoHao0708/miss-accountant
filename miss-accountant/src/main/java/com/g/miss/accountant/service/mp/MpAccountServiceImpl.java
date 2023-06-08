package com.g.miss.accountant.service.mp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g.miss.accountant.Template.AccountTemplate;
import com.g.miss.accountant.Template.SheetTemplate;
import com.g.miss.accountant.bean.Sheet;
import com.g.miss.accountant.dao.mp.MpAccountDao;
import com.g.miss.accountant.dao.mp.MpDebtDao;
import com.g.miss.accountant.entity.Account;
import com.g.miss.accountant.entity.Debt;
import com.g.miss.accountant.util.JsonUtils;
import com.linecorp.bot.model.message.FlexMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author G
 * @description 帳號
 * @date 2023/6/8 12:18 PM
 */
@Service
public class MpAccountServiceImpl extends ServiceImpl<MpAccountDao, Account> implements MpAccountService {

    @Autowired
    private MpAccountDao mpAccountDao;
    @Autowired
    private MpDebtDao mpDebtDao;

    /**
     * 更新帳號資訊
     *
     * @param groupId 群組id
     * @param userId  使用者id
     * @param name    名稱
     * @return 成功或失敗
     */
    @Override
    public boolean updateInfo(String groupId, String userId, String name) {
//        Account account = mpAccountDao.selectOne(new LambdaQueryWrapper<Account>()
//                .select(Account::getId, Account::getGroupId, Account::getUserId, Account::getName)
//                .eq(Account::getGroupId, groupId).eq(Account::getUserId, userId)
//                .last("Limit 1"));
        Account account = mpAccountDao.getAccountByGroupIdAndUserId(groupId, userId);
        if (account == null) {
            account = Account.builder().groupId(groupId).userId(userId).build();
        }
        account.setName(name);
        return this.saveOrUpdate(account);
    }

    /**
     * 取得群組內自己以外的其他人帳號
     *
     * @param groupId 群組id
     * @param userId  使用者id
     * @param name    名稱
     * @return 帳號列表
     */
    @Override
    public String listGroupUserExceptItself(String groupId, String userId, String name) {
        updateInfo(groupId, userId, name);
        List<Account> accountList = mpAccountDao.selectList(new LambdaQueryWrapper<Account>()
                .select(Account::getId, Account::getGroupId, Account::getUserId, Account::getName)
                .eq(Account::getGroupId, groupId).ne(Account::getUserId, userId));
        return JsonUtils.toJson(accountList);
    }

    public FlexMessage getAllotFlexMessage(String groupId) { // 分帳演算法
        List<Account> accountList = statGroupAmount(groupId);
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

    public FlexMessage getGroupAmountFlexMessage(String groupId) {
        List<Account> accountList = statGroupAmount(groupId);
        if (accountList.size() == 0) return null;
        return new AccountTemplate().get(accountList);
    }

    /**
     * 計算群組各用戶總計
     *
     * @param groupId 群組id
     * @return 帳號列表
     */
    private List<Account> statGroupAmount(String groupId) {

        List<Account> accountList = mpAccountDao.listAccountByGroupId(groupId);
        List<Debt> debtList = mpDebtDao.listDebtByGroupId(groupId, 0);

        for (Debt debt : debtList) { // todo 整理
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
