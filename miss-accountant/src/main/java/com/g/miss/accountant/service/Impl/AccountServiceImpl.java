package com.g.miss.accountant.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g.miss.accountant.Template.AccountTemplate;
import com.g.miss.accountant.Template.SheetTemplate;
import com.g.miss.accountant.bean.Sheet;
import com.g.miss.accountant.dao.AccountDao;
import com.g.miss.accountant.dto.AccountDTO;
import com.g.miss.accountant.entity.Account;
import com.g.miss.accountant.entity.Debt;
import com.g.miss.accountant.service.AccountService;
import com.g.miss.accountant.util.BeanCopyUtils;
import com.g.miss.accountant.vo.AccountVO;
import com.linecorp.bot.model.message.FlexMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.g.miss.accountant.constants.Constants.SET_ACCOUNT_SUCCESS;

/**
 * @author G
 * @description 帳號
 * @date 2023/6/8 12:18 PM
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountDao, Account> implements AccountService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private DebtServiceImpl debtService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateInfo(AccountVO accountVO) {

        Account account = this.getAccountByGroupIdAndUserId(accountVO.getGroupId(), accountVO.getUserId());
        if (account == null) {
            account = Account.builder().groupId(accountVO.getGroupId()).userId(accountVO.getUserId()).build();
        }
        account.setName(accountVO.getName());
        this.saveOrUpdate(account);

        return SET_ACCOUNT_SUCCESS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<AccountDTO> listGroupUserExceptItself(AccountVO accountVO) {
        updateInfo(accountVO);
        List<Account> accountList = accountDao.selectList(new LambdaQueryWrapper<Account>()
                .select(Account::getId, Account::getGroupId, Account::getUserId, Account::getName)
                .eq(Account::getGroupId, accountVO.getGroupId()).ne(Account::getUserId, accountVO.getUserId()));
        return BeanCopyUtils.copyList(accountList, AccountDTO.class);
    }

    @Override
    public FlexMessage getAllotFlexMessage(String groupId) { // 分帳演算法
        List<Account> accountList = statGroupAmount(groupId);
        List<Sheet> sheets = new ArrayList<>();

        while (accountList.size() != 0) { // 排序->取出最前跟最後的抵銷->循環
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

    @Override
    public FlexMessage getGroupAmountFlexMessage(String groupId) {
        List<Account> accountList = statGroupAmount(groupId);
        if (accountList.size() == 0) return null;
        return new AccountTemplate().get(accountList);
    }

    @Override
    public Account getAccountByGroupIdAndUserId(String groupId, String userId) {
        return accountDao.selectOne(new LambdaQueryWrapper<Account>()
                .select(Account::getId, Account::getGroupId, Account::getUserId, Account::getName)
                .eq(Account::getGroupId, groupId).eq(Account::getUserId, userId)
                .last("Limit 1"));
    }

    @Override
    public List<Account> listAccountByGroupId(String groupId) {
        return accountDao.selectList(new LambdaQueryWrapper<Account>()
                .select(Account::getId, Account::getGroupId, Account::getUserId, Account::getName)
                .eq(Account::getGroupId, groupId));
    }

    /**
     * 計算群組各用戶總計
     *
     * @param groupId 群組id
     * @return 帳號列表
     */
    private List<Account> statGroupAmount(String groupId) {

        List<Account> accountList = this.listAccountByGroupId(groupId);
        List<Debt> debtList = debtService.listDebtByGroupId(groupId);

        for (Debt debt : debtList) {
            Account account = accountList.stream()
                    .filter(a -> debt.getUserId().equals(a.getUserId()))
                    .findAny().orElse(null);
            account.addAmount(-debt.getAmount()); // 欠債人加上負債
            account = accountList.stream()
                    .filter(a -> debt.getCreditorId().equals(a.getUserId()))
                    .findAny().orElse(null);
            account.addAmount(debt.getAmount()); // 債權人加上金額
        }

        // 排除0的帳號
        accountList = accountList.stream()
                .filter(a -> a.getAmount() != 0)
                .collect(Collectors.toList());

        return accountList;
    }
}
