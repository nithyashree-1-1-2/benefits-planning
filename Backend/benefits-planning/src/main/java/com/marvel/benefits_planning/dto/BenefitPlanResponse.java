package com.marvel.benefits_planning.dto;

import com.marvel.benefits_planning.entity.PlanStatus;
import com.marvel.benefits_planning.entity.PlanType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BenefitPlanResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private PlanType type;
    private String description;
    private BigDecimal monthlyCost;
    private PlanStatus status;
    private LocalDateTime createdAt;
}