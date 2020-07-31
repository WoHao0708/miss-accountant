package com.g.miss.accountant.dao;

import com.g.miss.accountant.bean.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountInfoDao extends JpaRepository<AccountInfo, String> {

    AccountInfo findAccountInfoByUserIdAndGroupId(String userId, String groupId);

    List<AccountInfo> findAccountInfoByGroupId(String groupId);

    List<AccountInfo> findAccountInfoByGroupIdAndIsAdvance(String groupId, int isAdvance);
}
