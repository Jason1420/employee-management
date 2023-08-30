package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.help.DeletedEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedEmployeeRepository extends JpaRepository<DeletedEmployee, Long> {
    DeletedEmployee findOneById(Long id);

    DeletedEmployee findOneByEmail(String email);
}

