package com.g.miss.accountant.service;

import com.g.miss.accountant.TestBase;
import com.g.miss.accountant.service.Impl.PublicFundServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author G
 * @description 公款測試案例
 * @date 2023/6/9 11:09 AM
 */
public class PublicFundServiceTest extends TestBase {

    @Autowired
    PublicFundServiceImpl publicFundService;

    @Test
    public void testUpdateBalance() {

        String groupId = "13213";
        int amount = 1;
        int expected = publicFundService.getById(groupId).getBalance() + amount;

        publicFundService.updateBalance(groupId, amount);

        int actual = publicFundService.getById(groupId).getBalance();

        Assert.assertEquals(expected, actual);
    }


    @ParameterizedTest
    @CsvSource({
            "13213, 1",
            "13213, 10",
            "13213, -15",
    })
    public void csvTestUpdateBalance(String groupId, int amount) {

        int expected = publicFundService.getById(groupId).getBalance() + amount;

        publicFundService.updateBalance(groupId, amount);

        int actual = publicFundService.getById(groupId).getBalance();

        Assert.assertEquals(expected, actual);
    }
}
