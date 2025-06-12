package com.mihan.leave_request_api.controller;

import com.mihan.leave_request_api.dto.LeaveRequestDto;
import com.mihan.leave_request_api.model.LeaveRequest;
import com.mihan.leave_request_api.service.LeaveRequestService;
import com.mihan.leave_request_api.service.AuditService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('CREATE_LEAVE')")
    @PostMapping
    public ResponseEntity<LeaveRequest> create(@RequestBody LeaveRequestDto request, Authentication auth, HttpServletRequest httpRequest) {
        LeaveRequest created = service.create(request, auth);
        auditService.log(auth.getName(), "CREATE", "LeaveRequest", httpRequest.getRemoteAddr(), "Leave request created: ID=" + created.getId());
        return ResponseEntity.status(201).body(created);
    }

    @PreAuthorize("hasAuthority('GET_ALL_USERS')")
    @GetMapping("/all")
    public ResponseEntity<List<LeaveRequest>> getAllUser(Authentication auth, HttpServletRequest httpRequest) {
        List<LeaveRequest> list = service.getAllUsers();
        auditService.log(auth.getName(), "READ_ALL_USERS", "LeaveRequest", httpRequest.getRemoteAddr(), "Fetched " + list.size() + " leave requests");
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAuthority('GET_ALL')")
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAll(Authentication auth, HttpServletRequest httpRequest) {
        List<LeaveRequest> list = service.getAll(auth);
        auditService.log(auth.getName(), "READ_ALL", "LeaveRequest", httpRequest.getRemoteAddr(), "Fetched " + list.size() + " leave requests");
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAuthority('UPDATE_LEAVE')")
    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequest> update(@PathVariable long id, @RequestBody LeaveRequest request, Authentication auth, HttpServletRequest httpRequest) {
        LeaveRequest updated = service.update(id, request);
        auditService.log(auth.getName(), "UPDATE", "LeaveRequest", httpRequest.getRemoteAddr(), "Leave request updated: ID=" + id);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAuthority('DELETE_LEAVE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, Authentication auth, HttpServletRequest httpRequest) {
        service.delete(id);
        auditService.log(auth.getName(), "DELETE", "LeaveRequest", httpRequest.getRemoteAddr(), "Leave request deleted: ID=" + id);
        return ResponseEntity.noContent().build();
    }
}
