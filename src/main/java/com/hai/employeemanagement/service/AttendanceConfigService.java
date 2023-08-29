package com.hai.employeemanagement.service;

import com.hai.employeemanagement.dto.AttendanceConfigDTO;
import com.hai.employeemanagement.entity.AttendanceConfig;
import com.hai.employeemanagement.repository.AttendanceConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

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
    public void configAttendance(AttendanceConfig attendanceConfig) {
        attendanceConfig.setId(1L);
        attendanceConfigRepository.save(attendanceConfig);
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

    public boolean checkWorkingSaturday(LocalDate today, Double workingDaysOfWeek) {
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY && workingDaysOfWeek == 5.5) {
            return true;
        }
        return false;
    }

    public boolean checkWorkingHour(LocalTime endWork) {
        return LocalTime.now().isBefore(endWork);
    }

    public int checkLateMinutes(LocalTime startWork) {
        // startWork 08:00
        LocalTime currentTime = LocalTime.now(); // 08:17
        if (currentTime.isBefore(startWork)) {
            return 0;
        }
        LocalTime lateTime = currentTime.minusHours(startWork.getHour())
                .minusMinutes(startWork.getMinute()); // 00:17
        return lateTime.getMinute() + lateTime.getHour() * 60; // 17
    }

    public int checkEarlyMinutes(LocalTime endWork) {
        // endWork 17:00  earlyTime 00:30
        LocalTime currentTime = LocalTime.now(); // 17:30
        if (currentTime.isAfter(endWork)) {
            return 0;
        }
        LocalTime earlyTime = endWork.minusHours(currentTime.getHour())
                .minusMinutes(currentTime.getMinute()); // 00:05
        return earlyTime.getMinute() + earlyTime.getHour() * 60; // 5
    }

    public boolean checkValidCheckIn(int lateMinutes, Long lateTime) {
        return lateMinutes <= lateTime;
    }

    public boolean checkValidCheckOut(int earlyMinutes, LocalTime earlyTime) {
        return earlyMinutes <= (earlyTime.getMinute() + earlyTime.getHour() * 60);
    }
}
