package com.g.miss.accountant.service.mp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g.miss.accountant.dao.mp.MpDebtDao;
import com.g.miss.accountant.entity.Debt;
import org.springframework.stereotype.Service;

/**
 * @author G
 * @description 債權
 * @date 2023/6/8 12:35 PM
 */
@Service
public class MpDebtServiceImpl extends ServiceImpl<MpDebtDao, Debt> implements MpDebtService {
}
