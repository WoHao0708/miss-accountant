package com.g.miss.accountant.dao.mp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.g.miss.accountant.entity.Account;
import org.springframework.stereotype.Repository;

/**
 * @author G
 * @description 帳號
 * @date 2023/6/8 12:19 PM
 */

@Repository
public interface mpAccountDao extends BaseMapper<Account> {
}
