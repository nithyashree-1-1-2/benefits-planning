package com.marvel.benefits_planning.service;

import com.marvel.benefits_planning.dto.EmployeeRequest;
import com.marvel.benefits_planning.dto.EmployeeResponse;
import com.marvel.benefits_planning.entity.Employee;
import com.marvel.benefits_planning.exception.BusinessRuleException;
import com.marvel.benefits_planning.exception.ResourceNotFoundException;
import com.marvel.benefits_planning.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        if (repository.existsById(request.getEmployeeId())) {
            throw new BusinessRuleException("Employee already exists: " + request.getEmployeeId());
        }
        Employee employee = new Employee();
        employee.setEmployeeId(request.getEmployeeId());
        applyRequest(employee, request);
        return toResponse(repository.save(employee));
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getById(Long employeeId) {
        return toResponse(findOrThrow(employeeId));
    }

    @Transactional
    public EmployeeResponse update(Long employeeId, EmployeeRequest request) {
        Employee employee = findOrThrow(employeeId);
        applyRequest(employee, request);
        return toResponse(repository.save(employee));
    }

    private Employee findOrThrow(Long employeeId) {
        return repository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));
    }

    private void applyRequest(Employee employee, EmployeeRequest request) {
        employee.setFullName(request.getFullName());
        employee.setEmploymentStatus(request.getEmploymentStatus());
        employee.setMaritalStatus(request.getMaritalStatus());
        employee.setDateOfBirth(request.getDateOfBirth());
    }

    private EmployeeResponse toResponse(Employee e) {
        EmployeeResponse response = new EmployeeResponse();
        response.setEmployeeId(e.getEmployeeId());
        response.setFullName(e.getFullName());
        response.setEmploymentStatus(e.getEmploymentStatus());
        response.setMaritalStatus(e.getMaritalStatus());
        response.setDateOfBirth(e.getDateOfBirth());
        return response;
    }
}