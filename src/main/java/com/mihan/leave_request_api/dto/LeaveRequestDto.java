package com.mihan.leave_request_api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestDto {


    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

}
