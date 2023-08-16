package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.Attendance;
import com.hai.employeemanagement.entity.AttendanceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceConfigRepository extends JpaRepository<AttendanceConfig, Long> {
    AttendanceConfig findOneById(int i);
}
