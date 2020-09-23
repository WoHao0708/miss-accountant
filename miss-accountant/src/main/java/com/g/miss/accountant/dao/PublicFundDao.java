package com.g.miss.accountant.dao;

import com.g.miss.accountant.bean.PublicFund;
import com.g.miss.accountant.bean.Record;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PublicFundDao extends JpaRepository<PublicFund, String> {

    PublicFund findByGroupId(String groupId);
}
