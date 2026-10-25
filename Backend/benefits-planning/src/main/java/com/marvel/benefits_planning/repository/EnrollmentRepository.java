package com.marvel.benefits_planning.repository;

import com.marvel.benefits_planning.entity.Enrollment;
import com.marvel.benefits_planning.entity.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByEmployeeIdAndPlanIdAndStatus(Long employeeId, Long planId, EnrollmentStatus status);

    List<Enrollment> findByEmployeeId(Long employeeId);
}