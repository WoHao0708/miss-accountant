package com.g.miss.accountant.service;

import com.g.miss.accountant.TestBase;
import com.g.miss.accountant.entity.Debt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author G
 * @description
 * @date 2023/6/9 4:08 PM
 */
public class DebtServiceTest extends TestBase {

    @Autowired
    DebtService debtService;

    @Before
    public void setUp() throws Exception {

//        List<Debt> debtList = new ArrayList<>();
//        for (int i = 1; i < 10; i++) {
//            Debt debt = Debt.builder().id(i).groupId("1").userId(String.valueOf(i)).name(String.valueOf(i)).build();
//            debtList.add(debt);
//        }
    }

    @Test
    public void listDebtByGroupId() {
        String groupId = "1";

        int expect = 11;

        List<Debt> debtList = debtService.listDebtByGroupId(groupId);

        Assert.assertEquals(expect, debtList.size());
    }

    @Test
    public void listDebtByGroupIdAndUserId() {
        String groupId = "1";
        String userId = "9";

        int expect = 2;

        List<Debt> debtList = debtService.listDebtByGroupIdAndUserId(groupId, userId);

        Assert.assertEquals(expect, debtList.size());
    }

    @Test
    public void listDebtByGroupIdAndCreditorId() {
        String groupId = "1";
        String creditorId = "2";

        int expect = 6;

        List<Debt> debtList = debtService.listDebtByGroupIdAndCreditorId(groupId, creditorId);

        Assert.assertEquals(expect, debtList.size());
    }

    @Test
    public void addDebt() {
//        String[] userId = {"1", "3", "4", "5", "6", "9"};
//        String groupId = "1";
//        String creditor = "2";
//        String note = "test";
//        int amount = 78;
//
//        debtService.addDebt(groupId, userId, creditor, amount, note);
    }
}
