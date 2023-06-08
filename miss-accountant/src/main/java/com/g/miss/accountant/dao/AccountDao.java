package com.g.miss.accountant.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.g.miss.accountant.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author G
 * @description 帳號
 * @date 2023/6/8 12:19 PM
 */

@Repository
public interface AccountDao extends BaseMapper<Account> {

    /**
     * 取得帳號
     *
     * @param groupId 群組id
     * @param userId  使用者id
     * @return 帳號
     */
    Account getAccountByGroupIdAndUserId(@Param("groupId") String groupId, @Param("userId") String userId);

    /**
     * 取得群組內的帳號
     *
     * @param groupId 群組id
     * @return 帳號列表
     */
    List<Account> listAccountByGroupId(@Param("groupId") String groupId);
}
