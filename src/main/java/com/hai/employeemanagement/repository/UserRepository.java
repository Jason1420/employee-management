package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneByUsername(String username);

    UserEntity findOneById(Long id);

    UserEntity findOneByEmployeeId(Long id);
}
