package com.g.miss.accountant.dao;

import com.g.miss.accountant.bean.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountDao extends JpaRepository<Account, String> {

    Account findAccountInfoByUserIdAndGroupId(String userId, String groupId);

    List<Account> findAccountInfoByGroupId(String groupId);

    List<Account> findAccountInfoByGroupIdAndUserIdIsNot(String groupId, String userId);

    List<Account> findAccountInfoByGroupIdAndIsAdvance(String groupId, int isAdvance);
}
