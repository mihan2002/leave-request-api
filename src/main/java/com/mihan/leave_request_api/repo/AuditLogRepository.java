package com.mihan.leave_request_api.repo;

import com.mihan.leave_request_api.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
