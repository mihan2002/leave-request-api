package com.mihan.leveform.controller;

import com.mihan.leveform.dto.LeaveRequestDto;
import com.mihan.leveform.model.LeaveRequest;
import com.mihan.leveform.service.LeaveRequestService;
import com.mihan.leveform.service.AuditService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService service;

    @Autowired
    private AuditService auditService;

    @PostMapping
    public ResponseEntity<LeaveRequest> create(@RequestBody LeaveRequestDto request, Authentication auth, HttpServletRequest httpRequest) {
        LeaveRequest created = service.create(request, auth);
        auditService.log(auth.getName(), "CREATE", "LeaveRequest", httpRequest.getRemoteAddr(), "Leave request created: ID=" + created.getId());
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAll(Authentication auth, HttpServletRequest httpRequest) {
        List<LeaveRequest> list = service.getAll(auth);
        auditService.log(auth.getName(), "READ_ALL", "LeaveRequest", httpRequest.getRemoteAddr(), "Fetched " + list.size() + " leave requests");
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequest> update(@PathVariable int id, @RequestBody LeaveRequest request, Authentication auth, HttpServletRequest httpRequest) {
        LeaveRequest updated = service.update(id, request);
        auditService.log(auth.getName(), "UPDATE", "LeaveRequest", httpRequest.getRemoteAddr(), "Leave request updated: ID=" + id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, Authentication auth, HttpServletRequest httpRequest) {
        service.delete(id);
        auditService.log(auth.getName(), "DELETE", "LeaveRequest", httpRequest.getRemoteAddr(), "Leave request deleted: ID=" + id);
        return ResponseEntity.noContent().build();
    }
}
