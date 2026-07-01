package com.horseracing.project3.repository;

import com.horseracing.project3.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepo extends JpaRepository<AuditLog, Integer> {
    List<AuditLog> findAllByOrderByTimestampDesc();
}
