package com.mihan.leave_request_api.service;

import com.mihan.leave_request_api.model.AuditLog;
import com.mihan.leave_request_api.repo.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void log(String username, String action, String resource, String ip, String info) {
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setResource(resource);
        log.setTimestamp(LocalDateTime.now());
        log.setIpAddress(ip);
        log.setAdditionalInfo(info);

        auditLogRepository.save(log);
    }
}
