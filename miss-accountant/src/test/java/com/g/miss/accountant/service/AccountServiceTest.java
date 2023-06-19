package com.g.miss.accountant.service;

import com.g.miss.accountant.TestBase;
import com.g.miss.accountant.entity.Account;
import com.g.miss.accountant.vo.AccountVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author G
 * @description 帳號測試案例
 * @date 2023/6/9 12:56 PM
 */
public class AccountServiceTest extends TestBase {


    @Autowired
    private AccountService accountService;
    private final String groupId = "1";
    private final String userId = "1";
    private final String name = String.valueOf(new Random().nextInt(10000));
    private final AccountVO accountVO = new AccountVO(groupId, userId, name);


    @Before
    public void initializeAccount() {
        List<Account> accountList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Account account = Account.builder().id(i).groupId("1").userId(String.valueOf(i)).name(String.valueOf(i)).build();
            accountList.add(account);
        }

        accountService.saveOrUpdateBatch(accountList);
    }

    @Test
    public void testUpdateInfo() {

        accountService.updateInfo(accountVO);

        String actual = accountService.getAccountByGroupIdAndUserId(groupId, userId).getName();

        Assert.assertEquals(name, actual);
    }

    @Test
    public void testListGroupUserExceptItself() {
        int expectedSize = 8;

        List<Account> accountList = accountService.listGroupUserExceptItself(accountVO);

        accountList.forEach(account -> Assert.assertNotEquals(userId, account.getUserId())); // assert userId not in list
        Assert.assertEquals(expectedSize, accountList.size());
    }

    @Test
    public void testGetAccountByGroupIdAndUserId() {
        String expected = "8";

        Account account = accountService.getAccountByGroupIdAndUserId(groupId, expected);

        Assert.assertEquals(expected, account.getName());
    }

    @Test
    public void testListAccountByGroupId() {
        int expected = 9;

        List<Account> accountList = accountService.listAccountByGroupId(groupId);

        Assert.assertEquals(expected, accountList.size());
    }
}
