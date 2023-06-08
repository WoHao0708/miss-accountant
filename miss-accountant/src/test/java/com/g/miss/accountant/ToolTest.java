package com.g.miss.accountant;

import com.g.miss.accountant.entity.Account;
import com.g.miss.accountant.entity.PublicFund;
import com.g.miss.accountant.service.mp.MpAccountServiceImpl;
import com.g.miss.accountant.service.mp.MpPublicFundServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author G
 * @description
 * @date 2023/5/15 4:46 PM
 */
@PropertySource(value = "classpath:application.properties")
@SpringBootTest(classes = {AccountantApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class ToolTest {

    @Autowired
    private MpPublicFundServiceImpl mpPublicFundService;
    @Autowired
    private MpAccountServiceImpl mpAccountService;

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
