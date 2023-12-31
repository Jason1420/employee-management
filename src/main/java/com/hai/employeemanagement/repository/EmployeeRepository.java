package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findOneById(Long id);

    Employee findOneByEmail(String email);

    Employee findOneByCode(String code);
}
