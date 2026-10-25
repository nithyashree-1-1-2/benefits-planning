package com.marvel.benefits_planning.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EnrollmentRequest {

    @NotNull
    private Long employeeId;

    @NotNull
    private Long planId;

    @NotNull
    private LocalDate startDate;
}