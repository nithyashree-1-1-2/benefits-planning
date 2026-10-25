package com.marvel.benefits_planning.repository;

import com.marvel.benefits_planning.entity.BenefitPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BenefitPlanRepository extends JpaRepository<BenefitPlan, Long> {
}
