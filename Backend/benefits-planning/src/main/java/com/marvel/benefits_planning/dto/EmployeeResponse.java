package com.marvel.benefits_planning.dto;

import com.marvel.benefits_planning.entity.EmploymentStatus;
import com.marvel.benefits_planning.entity.MaritalStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class EmployeeResponse implements Serializable {

    private Long employeeId;
    private String fullName;
    private EmploymentStatus employmentStatus;
    private MaritalStatus maritalStatus;
    private LocalDate dateOfBirth;
}