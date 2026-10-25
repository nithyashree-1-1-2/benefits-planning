package com.marvel.benefits_planning.dto;

import com.marvel.benefits_planning.entity.PlanStatus;
import com.marvel.benefits_planning.entity.PlanType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BenefitPlanRequest {

    @NotBlank
    private String name;

    @NotNull
    private PlanType type;

    private String description;

    @NotNull
    @Positive
    private BigDecimal monthlyCost;

    @NotNull
    private PlanStatus status;
}