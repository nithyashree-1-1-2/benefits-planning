package com.marvel.benefits_planning.controller;

import com.marvel.benefits_planning.dto.EnrollmentRequest;
import com.marvel.benefits_planning.dto.EnrollmentResponse;
import com.marvel.benefits_planning.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService service;

    @PostMapping
    public ResponseEntity<EnrollmentResponse> enroll(@Valid @RequestBody EnrollmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.enroll(request));
    }

    @GetMapping("/employee/{employeeId}")
    public List<EnrollmentResponse> getByEmployee(@PathVariable Long employeeId) {
        return service.getByEmployee(employeeId);
    }

    @PutMapping("/{id}/cancel")
    public EnrollmentResponse cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}