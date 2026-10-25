package com.marvel.benefits_planning.dto;

import com.marvel.benefits_planning.entity.EmploymentStatus;
import com.marvel.benefits_planning.entity.MaritalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeRequest {

    @NotNull
    private Long employeeId;

    @NotBlank
    private String fullName;

    @NotNull
    private EmploymentStatus employmentStatus;

    @NotNull
    private MaritalStatus maritalStatus;

    @NotNull
    @Past
    private LocalDate dateOfBirth;
}