package com.hai.employeemanagement.service;

import com.hai.employeemanagement.converter.AttendanceConverter;
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
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceConverter attendanceConverter;
    private final EmployeeRepository employeeRepository;
    private final AttendanceConfigRepository attendanceConfigRepository;

    public AttendanceDTO checkIn(Long employeeId) {
        AttendanceConfig config = attendanceConfigRepository.findOneById(1);
        if (!checkWorkingDay(config.getWorkingDaysOfWeek())){
            throw new Exception409("Today no working");
        }
        if (!checkWorkingHour(config.getStartWorkTime(),config.getEndWorkTime())){
            throw new Exception409("Not in working time");
        }
        int lateMinutes = checkLateMinutes(config.getStartWorkTime());
        if(!checkValidCheckIn(lateMinutes, config.getLateAndEarlyTime())){
            throw new Exception409("You are so late");
        }
        Attendance attendanceToday = attendanceRepository.findByEmployeeIdAndDate(employeeId, LocalDate.now());
        if (attendanceToday != null) {
            throw new Exception409("You had checked in");
        }
        Employee employee = employeeRepository.findOneById(employeeId);
        Attendance attendance = new Attendance(LocalDate.now(),
                LocalTime.now(),
                Status.PRESENT,
                employee.getFirstName() + " " + employee.getLastName(),
                employeeId,
                lateMinutes);
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
        throw new Exception409("You had checked out");
    }

    public List<AttendanceDTO> viewAttendance(AttendanceViewDTO dto) {
        List<Attendance> list = attendanceRepository.findAllBetweenDate(dto.getStartDate(), dto.getEndDate());
        return list.stream().map(attendanceConverter::toDto).collect(Collectors.toList());
    }

    public List<AttendanceDTO> viewAttendanceOfEmployee(Long id, AttendanceViewDTO dto) {
        List<Attendance> list = attendanceRepository.findAllBetweenDateOfEmployee(dto.getStartDate(), dto.getEndDate(), id);
        return list.stream().map(attendanceConverter::toDto).collect(Collectors.toList());
    }

    public List<List<Object[]>> countAttendance(AttendanceViewDTO dto) {
//        List<CountAttendanceDTO> returnList = new ArrayList<>();
        List<List<Object[]>> list = attendanceRepository.countAttendance(dto.getStartDate(), dto.getEndDate());
        return list;
    }

    public boolean checkWorkingDay(Double workingDaysOfWeek) {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SUNDAY) {
            return false;
        }
        if (dayOfWeek == DayOfWeek.SATURDAY && workingDaysOfWeek == 5) {
            return false;
        }
        return true;
    }

    public boolean checkWorkingHour(LocalTime startWork, LocalTime endWork) {
        return LocalTime.now().isAfter(startWork) && LocalTime.now().isBefore(endWork);
    }

    public int checkLateMinutes(LocalTime startWork) {
        // startWork 08:00
        LocalTime currentTime = LocalTime.now(); // 08:17
        LocalTime lateTime = currentTime.minusHours(startWork.getHour())
                .minusMinutes(startWork.getMinute()); // 00:17
        return lateTime.getMinute() + lateTime.getHour() * 60; // 17
    }

    public int checkEarlyMinutes(LocalTime endWork) {
        // endWork 17:00  earlyTime 00:30
        LocalTime currentTime = LocalTime.now(); // 16:55
        LocalTime earlyTime = endWork.minusHours(currentTime.getHour())
                .minusMinutes(currentTime.getMinute()); // 00:05
        return earlyTime.getMinute() + earlyTime.getHour() * 60; // 5
    }

    public boolean checkValidCheckIn(int lateMinutes, LocalTime lateTime) {
        return lateMinutes <= (lateTime.getMinute() + lateTime.getHour()*60);
    }
    public boolean checkValidCheckOut(int earlyMinutes, LocalTime earlyTime) {
        return earlyMinutes <= (earlyTime.getMinute() + earlyTime.getHour()*60);
    }
}
