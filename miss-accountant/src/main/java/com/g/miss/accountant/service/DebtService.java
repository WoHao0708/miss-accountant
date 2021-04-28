package com.g.miss.accountant.service;

import com.g.miss.accountant.bean.Account;
import com.g.miss.accountant.bean.AjaxResponse;
import com.g.miss.accountant.bean.Debt;
import com.g.miss.accountant.dao.DebtDao;
import com.g.miss.accountant.enums.SuccessMsgEnum;
import com.g.miss.accountant.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DebtService {

    @Autowired
    DebtDao debtDao;
    @Autowired
    AccountService accountService;

    public String addDebt(String[] userIds, String creditor, String groupId, int amount, String note) {
        AjaxResponse ajaxResponse = new AjaxResponse();

        if (userIds == null || userIds.length == 0)
            ajaxResponse.setStatus(0);
        else {
            List<Debt> debts = new ArrayList<>();
            for (String userid : userIds) {
                Debt debt = new Debt(userid, groupId, creditor, amount, note);
                debts.add(debt);
            }
            debtDao.saveAll(debts);
            ajaxResponse.setStatus(1);
            ajaxResponse.setMessage(SuccessMsgEnum.getRandomMsg());
        }

        return ajaxResponse.toString();
    }

    public String getDebt(String groupId, String userId) {

        List<Debt> debtList = debtDao.findDebtByGroupIdAndUserIdAndIsDelete(groupId, userId, 0);
        List<Debt> ownDebtList = debtDao.findDebtByGroupIdAndCreditorAndIsDelete(groupId, userId, 0);
        List<Account> accountList = accountService.getGroupAllUser(groupId, userId);
        Map<String, String> nameMap = new HashMap<>();

        for (Account account: accountList) {
            nameMap.put(account.getUserId(), account.getName());
        }

        for (Debt debt: debtList) {
            debt.setName(nameMap.get(debt.getUserId()));
        }

        for (Debt debt: ownDebtList) {
            debt.setName(nameMap.get(debt.getUserId()));
            debt.setIsOwner(1);
        }
        debtList.addAll(ownDebtList);

        return JsonUtils.toJson(debtList);
    }

    public String deleteDebt(int debtId){
        AjaxResponse ajaxResponse = new AjaxResponse();
        Debt debt = debtDao.findDebtById(debtId);
        debt.setIsDelete(1);

        debtDao.save(debt);

        ajaxResponse.setStatus(1);

        return ajaxResponse.toString();
    }
}
