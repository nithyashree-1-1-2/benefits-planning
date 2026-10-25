package com.marvel.benefits_planning.service;

import com.marvel.benefits_planning.dto.BenefitPlanRequest;
import com.marvel.benefits_planning.dto.BenefitPlanResponse;
import com.marvel.benefits_planning.entity.BenefitPlan;
import com.marvel.benefits_planning.exception.ResourceNotFoundException;
import com.marvel.benefits_planning.repository.BenefitPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BenefitPlanService {

    private final BenefitPlanRepository repository;

    @Transactional
    @CacheEvict(value = "plans", allEntries = true)
    public BenefitPlanResponse create(BenefitPlanRequest request) {
        BenefitPlan plan = new BenefitPlan();
        applyRequest(plan, request);
        return toResponse(repository.save(plan));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "plans", key = "'all'")
    public List<BenefitPlanResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public BenefitPlanResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    @CacheEvict(value = "plans", allEntries = true)
    public BenefitPlanResponse update(Long id, BenefitPlanRequest request) {
        BenefitPlan plan = findOrThrow(id);
        applyRequest(plan, request);
        return toResponse(repository.save(plan));
    }

    @Transactional
    @CacheEvict(value = "plans", allEntries = true)
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private BenefitPlan findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Benefit plan not found: " + id));
    }

    private void applyRequest(BenefitPlan plan, BenefitPlanRequest request) {
        plan.setName(request.getName());
        plan.setType(request.getType());
        plan.setDescription(request.getDescription());
        plan.setMonthlyCost(request.getMonthlyCost());
        plan.setStatus(request.getStatus());
    }

    private BenefitPlanResponse toResponse(BenefitPlan plan) {
        BenefitPlanResponse response = new BenefitPlanResponse();
        response.setId(plan.getId());
        response.setName(plan.getName());
        response.setType(plan.getType());
        response.setDescription(plan.getDescription());
        response.setMonthlyCost(plan.getMonthlyCost());
        response.setStatus(plan.getStatus());
        response.setCreatedAt(plan.getCreatedAt());
        return response;
    }
}