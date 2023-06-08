package com.g.miss.accountant.service.mp;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g.miss.accountant.entity.Account;

/**
 * @author G
 * @description 帳號
 * @date 2023/6/8 12:17 PM
 */
public interface MpAccountService extends IService<Account> {

    // 更新帳號資訊
    boolean updateInfo(String groupId, String userId, String name);

    String listGroupUserExceptItself(String groupId, String userId, String name);
}
