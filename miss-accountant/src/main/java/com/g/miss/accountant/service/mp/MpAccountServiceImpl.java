package com.g.miss.accountant.service.mp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g.miss.accountant.dao.mp.mpAccountDao;
import com.g.miss.accountant.entity.Account;
import org.springframework.stereotype.Service;

/**
 * @author G
 * @description 帳號
 * @date 2023/6/8 12:18 PM
 */
@Service
public class MpAccountServiceImpl extends ServiceImpl<mpAccountDao, Account> implements MpAccountService {
}
