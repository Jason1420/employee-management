package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.AttendanceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceConfigRepository extends JpaRepository<AttendanceConfig, Long> {
    AttendanceConfig findOneById(int i);
}
