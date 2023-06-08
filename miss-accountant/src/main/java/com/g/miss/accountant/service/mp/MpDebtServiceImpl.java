package com.g.miss.accountant.service.mp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g.miss.accountant.constants.Constants;
import com.g.miss.accountant.dao.mp.MpDebtDao;
import com.g.miss.accountant.entity.Debt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author G
 * @description 債權
 * @date 2023/6/8 12:35 PM
 */
@Service
public class MpDebtServiceImpl extends ServiceImpl<MpDebtDao, Debt> implements MpDebtService {

    @Autowired
    MpDebtDao mpDebtDao;

    @Override
    public String deleteGroupDebt(String groupId) {

        List<Debt> debtList = mpDebtDao.listDebtByGroupId(groupId, 0);

        for (Debt debt : debtList) debt.setIsDelete(1);

        this.saveBatch(debtList);

        return Constants.RESET_SUCCESS;
    }
}
