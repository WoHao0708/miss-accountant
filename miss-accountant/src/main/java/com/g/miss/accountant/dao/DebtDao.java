package com.g.miss.accountant.dao;

import com.g.miss.accountant.bean.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtDao extends JpaRepository<Debt, String> {

    List<Debt> findDebtByGroupIdAndUserIdAndIsDelete(String groupId, String userId, int isDelete);

    List<Debt> findDebtByGroupIdAndCreditorAndIsDelete(String groupId, String creditor, int isDelete);

    Debt findDebtById(int id);
}
