package com.mihan.leave_request_api.repo;

import com.mihan.leave_request_api.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest,Long> {

    List<LeaveRequest> findByUserId(Long userId);
}
