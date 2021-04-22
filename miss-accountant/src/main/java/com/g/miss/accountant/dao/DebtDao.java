package com.g.miss.accountant.dao;

import com.g.miss.accountant.bean.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtDao extends JpaRepository<Debt, String> {

    List<Debt> findDebtByGroupIdAndUserId(String groupId, String userId);

    List<Debt> findDebtByGroupIdAndCreditor(String groupId, String creditor);
}
