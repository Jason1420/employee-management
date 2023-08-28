package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findOneById(Long id);

    Department findOneByName(String departmentName);
}
