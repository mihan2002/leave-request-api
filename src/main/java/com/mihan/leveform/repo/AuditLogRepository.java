package com.mihan.leveform.repo;

import com.mihan.leveform.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
