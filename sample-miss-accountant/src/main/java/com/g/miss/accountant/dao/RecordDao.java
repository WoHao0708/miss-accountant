package com.g.miss.accountant.dao;

import com.g.miss.accountant.bean.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Repository
public interface RecordDao extends JpaRepository<Record, String> {

    List<Record> findByUserIdAndGroupIdAndCreatedTimeAfter(String userId, String groupId, Date createdTime);

    List<Record> findByUserIdAndGroupIdOrderByIdDesc(String userId, String groupId, Pageable pageable);
}
