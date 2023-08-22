package com.hai.employeemanagement.service;

import com.hai.employeemanagement.converter.AttendanceConverter;
import com.hai.employeemanagement.dto.AttendanceDTO;
import com.hai.employeemanagement.dto.help.AttendanceViewDTO;
import com.hai.employeemanagement.dto.help.CountAttendanceDTO;
import com.hai.employeemanagement.entity.Attendance;
import com.hai.employeemanagement.entity.AttendanceConfig;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.help.Status;
import com.hai.employeemanagement.exception.Exception409;
import com.hai.employeemanagement.filecsv.Helper;
import com.hai.employeemanagement.repository.AttendanceConfigRepository;
import com.hai.employeemanagement.repository.AttendanceRepository;
import com.hai.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceConverter attendanceConverter;
    private final EmployeeRepository employeeRepository;
    private final AttendanceConfigRepository attendanceConfigRepository;
    private final AttendanceConfigService attendanceConfigService;
    private final Helper helper;

    public AttendanceDTO checkIn(Long employeeId) {
        AttendanceConfig config = attendanceConfigRepository.findOneById(1);
        if (!attendanceConfigService.checkWorkingDay(config.getWorkingDaysOfWeek())) {
            throw new Exception409("Today no working");
        }
        if (!attendanceConfigService.checkWorkingHour(config.getEndWorkTime())) {
            throw new Exception409("Not in working time");
        }
        int lateMinutes = attendanceConfigService.checkLateMinutes(config.getStartWorkTime());
        Status status = Status.PRESENT;
        if (lateMinutes > 5) { //
            status = Status.LATE;
        }
        if (!attendanceConfigService.checkValidCheckIn(lateMinutes, config.getLateAndEarlyTime())) {
            status = Status.ABSENT;
        }
        Attendance attendanceToday = attendanceRepository.findByEmployeeIdAndDate(employeeId, LocalDate.now());
        if (attendanceToday != null) {
            throw new Exception409("You had checked in");
        }
        Employee employee = employeeRepository.findOneById(employeeId);
        Attendance attendance = new Attendance(LocalDate.now(),
                LocalTime.now(),
                status,
                employee.getFirstName() + " " + employee.getLastName(),
                employeeId,
                lateMinutes);
        attendanceRepository.save(attendance);
        return attendanceConverter.toDto(attendance);
    }

    public AttendanceDTO checkOut(Long employeeId) {
        AttendanceConfig config = attendanceConfigRepository.findOneById(1);
        Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, LocalDate.now());
        if (!attendanceConfigService.checkWorkingDay(config.getWorkingDaysOfWeek())) {
            throw new Exception409("Today no working");
        }
        if (attendance != null && attendance.getCheckOutTime() == null) {
            int earlyTime = attendanceConfigService.checkEarlyMinutes(config.getEndWorkTime());
            if (attendanceConfigService.checkWorkingSaturday(attendance.getDate(), config.getWorkingDaysOfWeek())) {
                earlyTime = attendanceConfigService.checkEarlyMinutes(config.getStartWorkTime().plusHours(4));
            }
            attendance.setCheckOutTime(LocalTime.now());
            attendance.setEarlyMinutes(earlyTime);
            attendanceRepository.save(attendance);
            return attendanceConverter.toDto(attendance);
        }
        throw new Exception409("You had checked out");
    }

    public List<AttendanceDTO> viewAttendance(AttendanceViewDTO dto) {
        List<Attendance> list = attendanceRepository.findAllBetweenDate(dto.getStartDate(), dto.getEndDate());
        return list.stream().map(attendanceConverter::toDto).collect(Collectors.toList());
    }

//    public List<AttendanceDTO> viewAttendanceOfEmployee(Long id, AttendanceViewDTO dto) {
//        List<Attendance> list = attendanceRepository.findAllBetweenDateOfEmployee(dto.getStartDate(), dto.getEndDate(), id);
//        return list.stream().map(attendanceConverter::toDto).collect(Collectors.toList());
//    }
    public List<Attendance> viewAttendanceOfEmployee(Long id, AttendanceViewDTO dto) {
        List<Attendance> list = attendanceRepository.findAllBetweenDateOfEmployee(dto.getStartDate(), dto.getEndDate(), id);
        return list;
    }

    public List<CountAttendanceDTO> countAttendance(AttendanceViewDTO dto) {
        List<CountAttendanceDTO> returnList = new ArrayList<>();
        List<List<Object[]>> list = attendanceRepository.countAttendance(dto.getStartDate(), dto.getEndDate());
        list.stream()
                .map(data -> returnList.add(new CountAttendanceDTO(
                                        Long.parseLong(Arrays.toString(data.get(0)).replaceAll("\\[|\\]", "")),
                                        Arrays.toString(data.get(1)).replaceAll("\\[|\\]", ""),
                                        Integer.parseInt(Arrays.toString(data.get(2)).replaceAll("\\[|\\]", ""))
                                )
                        )
                ).collect(Collectors.toList());
        return returnList;
    }

    public ByteArrayInputStream getActualData() {
        List<Attendance> all = attendanceRepository.findAll();
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = helper.dataToExcel(all);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArrayInputStream;
    }
    public ByteArrayInputStream getActualData(AttendanceViewDTO dto) {
        List<CountAttendanceDTO> all = countAttendance(dto);
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = helper.dataCountToExcel(all);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArrayInputStream;
    }

    public Attendance viewAttendanceOfEmployeeToday(Long id) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findOneByEmployeeIdAndDate(id,today);
        return attendance;
    }
}
