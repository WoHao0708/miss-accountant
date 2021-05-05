package com.g.miss.accountant.dao;

import com.g.miss.accountant.bean.PublicFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicFundDao extends JpaRepository<PublicFund, String> {

    PublicFund findByGroupId(String groupId);
}
