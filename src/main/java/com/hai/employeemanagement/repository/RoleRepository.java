package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String role);

    Role findOneByName(String student);

    Set<Role> findAllByName(String employee);
}
