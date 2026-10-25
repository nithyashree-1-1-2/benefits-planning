package com.marvel.benefits_planning.repository;

import com.marvel.benefits_planning.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}