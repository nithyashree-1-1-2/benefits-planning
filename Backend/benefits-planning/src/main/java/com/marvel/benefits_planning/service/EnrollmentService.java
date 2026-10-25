package com.marvel.benefits_planning.service;

import com.marvel.benefits_planning.dto.EnrollmentRequest;
import com.marvel.benefits_planning.dto.EnrollmentResponse;
import com.marvel.benefits_planning.entity.BenefitPlan;
import com.marvel.benefits_planning.entity.ChangeLog;
import com.marvel.benefits_planning.entity.Enrollment;
import com.marvel.benefits_planning.entity.EnrollmentStatus;
import com.marvel.benefits_planning.entity.PlanStatus;
import com.marvel.benefits_planning.exception.BusinessRuleException;
import com.marvel.benefits_planning.exception.ResourceNotFoundException;
import com.marvel.benefits_planning.repository.BenefitPlanRepository;
import com.marvel.benefits_planning.repository.ChangeLogRepository;
import com.marvel.benefits_planning.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final BenefitPlanRepository planRepository;
    private final ChangeLogRepository changeLogRepository;

    @Transactional
    public EnrollmentResponse enroll(EnrollmentRequest request) {
        BenefitPlan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Benefit plan not found: " + request.getPlanId()));

        if (plan.getStatus() != PlanStatus.ACTIVE) {
            throw new BusinessRuleException("Plan is not active");
        }

        if (enrollmentRepository.existsByEmployeeIdAndPlanIdAndStatus(
                request.getEmployeeId(), request.getPlanId(), EnrollmentStatus.ACTIVE)) {
            throw new BusinessRuleException("Employee is already enrolled in this plan");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setEmployeeId(request.getEmployeeId());
        enrollment.setPlan(plan);
        enrollment.setStartDate(request.getStartDate());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        Enrollment saved = enrollmentRepository.save(enrollment);

        logChange(saved.getId(), "ENROLLED", "Employee " + saved.getEmployeeId() + " enrolled in plan " + plan.getName());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponse> getByEmployee(Long employeeId) {
        return enrollmentRepository.findByEmployeeId(employeeId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public EnrollmentResponse cancel(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found: " + id));

        if (enrollment.getStatus() == EnrollmentStatus.CANCELLED) {
            throw new BusinessRuleException("Enrollment is already cancelled");
        }

        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        enrollment.setEndDate(LocalDate.now());
        Enrollment saved = enrollmentRepository.save(enrollment);

        logChange(saved.getId(), "CANCELLED", "Enrollment cancelled, end date " + saved.getEndDate());
        return toResponse(saved);
    }

    private void logChange(Long enrollmentId, String action, String details) {
        ChangeLog log = new ChangeLog();
        log.setEntityType("ENROLLMENT");
        log.setEntityId(enrollmentId);
        log.setAction(action);
        log.setPerformedBy(currentUsername());
        log.setDetails(details);
        changeLogRepository.save(log);
    }

    private String currentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "SYSTEM";
    }

    private EnrollmentResponse toResponse(Enrollment e) {
        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(e.getId());
        response.setEmployeeId(e.getEmployeeId());
        response.setPlanId(e.getPlan().getId());
        response.setPlanName(e.getPlan().getName());
        response.setStartDate(e.getStartDate());
        response.setEndDate(e.getEndDate());
        response.setStatus(e.getStatus());
        return response;
    }
}