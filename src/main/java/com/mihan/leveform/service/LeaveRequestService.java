package com.mihan.leveform.service;

import com.mihan.leveform.dto.LeaveRequestDto;
import com.mihan.leveform.model.LeaveRequest;
import com.mihan.leveform.model.User;
import com.mihan.leveform.repo.LeaveRequestRepo;
import com.mihan.leveform.exceptions.ResourceNotFoundException;
import com.mihan.leveform.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepo leaveRequestRepo;

    @Autowired
    private UserRepo userRepo;

    public LeaveRequest create(LeaveRequestDto request, Authentication authentication) {

        User user = userRepo.findByUsername(authentication.getName()).get();


        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setUserId(user.getId());
        leaveRequest.setType(request.getType());
        leaveRequest.setStartDate(request.getStartDate());
        leaveRequest.setEndDate(request.getEndDate());
        leaveRequest.setReason(request.getReason());

        return leaveRequestRepo.save(leaveRequest);
    }

    public List<LeaveRequest> getAll(Authentication authentication) {

        User user = userRepo.findByUsername(authentication.getName()).get();

        return leaveRequestRepo.findByUserId(user.getId());
    }

    public LeaveRequest update(int id, LeaveRequest updatedRequest) {

        LeaveRequest existingRequest = leaveRequestRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with ID: " + id));


        existingRequest.setType(updatedRequest.getType());
        existingRequest.setStartDate(updatedRequest.getStartDate());
        existingRequest.setEndDate(updatedRequest.getEndDate());
        existingRequest.setReason(updatedRequest.getReason());

        return leaveRequestRepo.save(existingRequest);
    }


    public void delete(int id) {
        if (!leaveRequestRepo.existsById(id)) {
            throw new ResourceNotFoundException("Leave request not found with ID: " + id);
        }
        leaveRequestRepo.deleteById(id);
    }
}
