package com.g.miss.accountant.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g.miss.accountant.dao.DebtDao;
import com.g.miss.accountant.dto.DebtDTO;
import com.g.miss.accountant.entity.Account;
import com.g.miss.accountant.entity.Debt;
import com.g.miss.accountant.enums.SuccessMsgEnum;
import com.g.miss.accountant.service.DebtService;
import com.g.miss.accountant.util.BeanCopyUtils;
import com.g.miss.accountant.vo.DebtVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.g.miss.accountant.constants.Constants.RESET_SUCCESS;
import static com.g.miss.accountant.enums.StatusCodeEnum.SUCCESS;

/**
 * @author G
 * @description 債權
 * @date 2023/6/8 12:35 PM
 */
@Service
public class DebtServiceImpl extends ServiceImpl<DebtDao, Debt> implements DebtService {

    @Autowired
    DebtDao debtDao;
    @Autowired
    AccountServiceImpl accountService;

    @Override
    public List<Debt> listDebtByGroupId(String groupId) {
        return debtDao.selectList(new LambdaQueryWrapper<Debt>()
                .select(Debt::getId, Debt::getGroupId, Debt::getUserId, Debt::getCreditorId, Debt::getAmount, Debt::getNote, Debt::getIsDelete)
                .eq(Debt::getGroupId, groupId).eq(Debt::getIsDelete, 0));
    }

    @Override
    public List<Debt> listDebtByGroupIdAndUserId(String groupId, String userId) {
        return debtDao.selectList(new LambdaQueryWrapper<Debt>()
                .select(Debt::getId, Debt::getGroupId, Debt::getUserId, Debt::getCreditorId, Debt::getAmount, Debt::getNote, Debt::getIsDelete)
                .eq(Debt::getGroupId, groupId).eq(Debt::getUserId, userId).eq(Debt::getIsDelete, 0));
    }

    @Override
    public List<Debt> listDebtByGroupIdAndCreditorId(String groupId, String creditorId) {
        return debtDao.selectList(new LambdaQueryWrapper<Debt>()
                .select(Debt::getId, Debt::getGroupId, Debt::getUserId, Debt::getCreditorId, Debt::getAmount, Debt::getNote, Debt::getIsDelete)
                .eq(Debt::getGroupId, groupId).eq(Debt::getCreditorId, creditorId).eq(Debt::getIsDelete, 0));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addDebt(DebtVO debtVO) {

        List<Debt> debts = new ArrayList<>();
        for (String userid : debtVO.getUserIds()) {
            Debt debt = Debt.builder().groupId(debtVO.getGroupId()).userId(userid).creditorId(debtVO.getUserId()).amount(debtVO.getAmount()).note(debtVO.getNote()).build();
            debts.add(debt);
        }
        this.saveBatch(debts);
        return SuccessMsgEnum.getRandomMsg();
    }

    @Override
    public List<DebtDTO> listDebt(String groupId, String userId) {

        List<Debt> debtList = this.listDebtByGroupIdAndUserId(groupId, userId);
        List<Debt> ownDebtList = this.listDebtByGroupIdAndCreditorId(groupId, userId);
        List<Account> accountList = accountService.listAccountByGroupId(groupId);
        Map<String, String> nameMap = new HashMap<>();

        for (Account account : accountList) {
            nameMap.put(account.getUserId(), account.getName());
        }

        for (Debt debt : debtList) {
            debt.setName(nameMap.get(debt.getCreditorId()));
        }

        for (Debt debt : ownDebtList) {
            debt.setName(nameMap.get(debt.getUserId()));
            debt.setIsOwner(1);
        }
        debtList.addAll(ownDebtList);

        return BeanCopyUtils.copyList(debtList, DebtDTO.class);
    }

    @Override
    public String deleteDebt(int debtId) {
        Debt debt = debtDao.selectById(debtId);
        debt.setIsDelete(1);
        debtDao.updateById(debt);

        return SUCCESS.getDesc();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteGroupDebt(String groupId) {

        List<Debt> debtList = this.listDebtByGroupId(groupId);

        for (Debt debt : debtList) debt.setIsDelete(1);

        this.saveBatch(debtList);

        return RESET_SUCCESS;
    }
}
