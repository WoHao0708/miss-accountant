package com.g.miss.accountant.service;

import com.g.miss.accountant.bean.AjaxResponse;
import com.g.miss.accountant.bean.Debt;
import com.g.miss.accountant.dao.AccountDao;
import com.g.miss.accountant.dao.DebtDao;
import com.g.miss.accountant.enums.SuccessMsgEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DebtService {

    @Autowired
    DebtDao debtDao;

    public String addDebt(String[] userIds, String creditor, String groupId, int amount, String note){
        AjaxResponse ajaxResponse = new AjaxResponse();

        if (userIds == null || userIds.length == 0)
            ajaxResponse.setStatus(0);
        else {
            List<Debt> debts = new ArrayList<>();
            for (String userid: userIds){
                Debt debt = new Debt(userid, groupId, creditor, amount, note);
                debts.add(debt);
            }
            debtDao.saveAll(debts);
            ajaxResponse.setStatus(1);
            ajaxResponse.setMessage(SuccessMsgEnum.getRandomMsg()); // todo 隨機回覆文字
        }

        return ajaxResponse.toString();
    }
}
