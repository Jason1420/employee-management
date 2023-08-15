package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUsername(String username);

    User findOneById(Long id);
}
