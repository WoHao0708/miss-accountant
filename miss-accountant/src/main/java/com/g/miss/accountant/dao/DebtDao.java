package com.g.miss.accountant.dao;

import com.g.miss.accountant.bean.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtDao extends JpaRepository<Debt, String> {

}
