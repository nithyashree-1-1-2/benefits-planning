package com.marvel.benefits_planning.dto;

import com.marvel.benefits_planning.entity.EnrollmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EnrollmentResponse {

    private Long id;
    private Long employeeId;
    private Long planId;
    private String planName;
    private LocalDate startDate;
    private LocalDate endDate;
    private EnrollmentStatus status;
}