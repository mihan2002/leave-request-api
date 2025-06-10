package com.mihan.leveform.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "leave_requests")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}
