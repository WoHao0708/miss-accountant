package com.g.miss.accountant;

import com.g.miss.accountant.entity.Account;
import com.g.miss.accountant.entity.PublicFund;
import com.g.miss.accountant.service.Impl.AccountServiceImpl;
import com.g.miss.accountant.service.Impl.PublicFundServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author G
 * @description
 * @date 2023/5/15 4:46 PM
 */

public class ToolTest extends TestBase {

    @Autowired
    private PublicFundServiceImpl mpPublicFundService;
    @Autowired
    private AccountServiceImpl mpAccountService;

    @Test
    public void mpTest() {
        PublicFund publicFund = new PublicFund();
        publicFund.setGroupId("13213");
        publicFund.setBalance(publicFund.getBalance() + 1);
        System.out.println(mpPublicFundService.saveOrUpdate(publicFund));
        System.out.println(mpPublicFundService.getById("13213").toString());
    }

    @Test
    public void accountTest() {
        Account account = new Account();
        account.setGroupId("1");
        account.setUserId("1");
        account.setName("test");
        System.out.println(mpAccountService.saveOrUpdate(account));
    }

}
