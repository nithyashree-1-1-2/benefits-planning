package com.marvel.benefits_planning.controller;

import com.marvel.benefits_planning.dto.BenefitPlanRequest;
import com.marvel.benefits_planning.dto.BenefitPlanResponse;
import com.marvel.benefits_planning.service.BenefitPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class BenefitPlanController {

    private final BenefitPlanService service;

    @PostMapping
    public ResponseEntity<BenefitPlanResponse> create(@Valid @RequestBody BenefitPlanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public List<BenefitPlanResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BenefitPlanResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public BenefitPlanResponse update(@PathVariable Long id, @Valid @RequestBody BenefitPlanRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}