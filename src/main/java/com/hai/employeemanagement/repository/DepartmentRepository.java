package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.Department;
import com.hai.employeemanagement.entity.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findOneById(Long id);

    List<Department> findAll();

    Department findOneByName(String departmentName);
}
