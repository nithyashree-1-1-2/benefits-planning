package com.marvel.benefits_planning.controller;

import com.marvel.benefits_planning.dto.EmployeeRequest;
import com.marvel.benefits_planning.dto.EmployeeResponse;
import com.marvel.benefits_planning.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_ADMIN')")
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse getById(@PathVariable Long employeeId) {
        return service.getById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse update(@PathVariable Long employeeId,
                                   @Valid @RequestBody EmployeeRequest request) {
        return service.update(employeeId, request);
    }
}