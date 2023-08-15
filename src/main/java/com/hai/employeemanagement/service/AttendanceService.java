package com.hai.employeemanagement.service;

import com.hai.employeemanagement.converter.AttendanceConverter;
import com.hai.employeemanagement.dto.AttendanceDTO;
import com.hai.employeemanagement.dto.help.AttendanceViewDTO;
import com.hai.employeemanagement.dto.help.CountAttendanceDTO;
import com.hai.employeemanagement.entity.Attendance;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.help.Status;
import com.hai.employeemanagement.exception.Exception409;
import com.hai.employeemanagement.repository.AttendanceRepository;
import com.hai.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceConverter attendanceConverter;

    public AttendanceDTO checkIn(Long employeeId) {
        Attendance attendanceToday = attendanceRepository.findByEmployeeIdAndDate(employeeId, LocalDate.now());
        if (attendanceToday != null) {
            throw new Exception409("You was checked in");
        }
        Employee employee = employeeRepository.findOneById(employeeId);
        Attendance attendance = new Attendance(LocalDate.now(),
                LocalTime.now(),
                Status.PRESENT,
                employee.getFirstName() + " " + employee.getLastName(),
                employeeId);
        attendanceRepository.save(attendance);
        return attendanceConverter.toDto(attendance);
    }

    public AttendanceDTO checkOut(Long employeeId) {
        Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, LocalDate.now());
        if (attendance != null && attendance.getCheckOutTime() == null) {
            attendance.setCheckOutTime(LocalTime.now());
            attendanceRepository.save(attendance);
            return attendanceConverter.toDto(attendance);
        }
        throw new Exception409("You was checked out");
    }

    public List<AttendanceDTO> viewAttendance(AttendanceViewDTO dto) {
        List<Attendance> list = attendanceRepository.findAllBetweenDate(dto.getStartDate(), dto.getEndDate());
        return list.stream().map(attendanceConverter::toDto).collect(Collectors.toList());
    }

    public List<AttendanceDTO> viewAttendanceOfEmployee(Long id, AttendanceViewDTO dto) {
        List<Attendance> list = attendanceRepository.findAllBetweenDateOfEmployee(dto.getStartDate(), dto.getEndDate(),id);
        return list.stream().map(attendanceConverter::toDto).collect(Collectors.toList());
    }

    public List<List<Object[]>> countAttendance(AttendanceViewDTO dto) {
        List<CountAttendanceDTO> returnList = new ArrayList<>();
        List<List<Object[]>> list = attendanceRepository.countAttendance(dto.getStartDate(), dto.getEndDate());
        return list;
    }
}
