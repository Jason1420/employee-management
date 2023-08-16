package com.hai.employeemanagement.service;

import com.hai.employeemanagement.converter.AttendanceConverter;
import com.hai.employeemanagement.dto.AttendanceConfigDTO;
import com.hai.employeemanagement.dto.AttendanceDTO;
import com.hai.employeemanagement.dto.help.AttendanceViewDTO;
import com.hai.employeemanagement.entity.Attendance;
import com.hai.employeemanagement.entity.AttendanceConfig;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.help.Status;
import com.hai.employeemanagement.exception.Exception409;
import com.hai.employeemanagement.repository.AttendanceConfigRepository;
import com.hai.employeemanagement.repository.AttendanceRepository;
import com.hai.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceConfigService {
    private final AttendanceConfigRepository attendanceConfigRepository;


    public AttendanceConfigDTO configAttendance(AttendanceConfigDTO dto) {
        AttendanceConfig config = new AttendanceConfig(1L,
                dto.getWorkingDaysOfWeek(),
                dto.getStartWorkTime(),
                dto.getEndWorkTime(),
                dto.getLateAndEarlyTime());
        attendanceConfigRepository.save(config);
        dto.setId(1L);
        return dto;
    }
}
