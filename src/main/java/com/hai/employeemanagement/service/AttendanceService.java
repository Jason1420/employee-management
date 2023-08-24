package com.hai.employeemanagement.service;

import com.hai.employeemanagement.converter.AttendanceConverter;
import com.hai.employeemanagement.dto.AttendanceDTO;
import com.hai.employeemanagement.dto.help.AttendanceViewDTO;
import com.hai.employeemanagement.dto.help.CountAttendanceDTO;
import com.hai.employeemanagement.dto.help.ResultAttendanceDTO;
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
import java.util.Random;
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
            int checkInHour = attendance.getCheckInTime().getHour();
            int checkInMinutes = attendance.getCheckInTime().getMinute();
            LocalTime punchTime = LocalTime.now().minusHours(checkInHour).minusMinutes(checkInMinutes);
            double breakHour = config.getBreakTime().getHour() + config.getBreakTime().getMinute() / 60.0;
            double punchHour = punchTime.getHour() + punchTime.getMinute() / 60.0 - breakHour;
            attendance.setPunchHour(punchHour);
            attendanceRepository.save(attendance);
            return attendanceConverter.toDto(attendance);
        }
        throw new Exception409("You had checked out");
    }

    public List<Attendance> viewAttendance(AttendanceViewDTO dto) {
        List<Attendance> list = attendanceRepository.findAllBetweenDate(dto.getStartDate(), dto.getEndDate());
        return list;
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
        Attendance attendance = attendanceRepository.findOneByEmployeeIdAndDate(id, today);
        return attendance;
    }

    public void checkInData(Long employeeId) {
        AttendanceConfig config = attendanceConfigRepository.findOneById(1);

        Employee employee = employeeRepository.findOneById(employeeId);
        LocalDate currentDate = LocalDate.now();
        for (int i = 1; i <= 7; i++) {
            LocalDate tempDate = currentDate.withMonth(i);
            int leave1 = new Random().nextInt(tempDate.lengthOfMonth());
            int leave2 = new Random().nextInt(tempDate.lengthOfMonth());
            int leave3 = new Random().nextInt(tempDate.lengthOfMonth());
            for (int k = 1; k <= tempDate.lengthOfMonth(); k++) {
                if(k == leave1 || k == leave2 || k == leave3) continue;
                LocalDate today = LocalDate.of(2023, i, k);
                if (today.getDayOfWeek().getValue() == 6 || today.getDayOfWeek().getValue() == 7) {
                    continue;
                }
                int minute = new Random().nextInt(60);
                LocalTime checkInTime = LocalTime.of(8, minute);
                int lateMinutes = Math.max(checkInTime.getMinute() - config.getStartWorkTime().getMinute(), 0);
                Status status = Status.PRESENT;
                if (lateMinutes > config.getLateAndEarlyTime().getHour() + config.getLateAndEarlyTime().getMinute() * 60) { //
                    status = Status.LATE;
                }
                Attendance attendance = new Attendance(today,
                        checkInTime,
                        status,
                        employee.getFirstName() + " " + employee.getLastName(),
                        employeeId,
                        lateMinutes);
                attendanceRepository.save(attendance);
            }

        }
    }

    public void checkOutData(Long employeeId) {
        AttendanceConfig config = attendanceConfigRepository.findOneById(1);
        LocalDate currentDate = LocalDate.now();
        for (int i = 1; i <= 7; i++) {
            LocalDate tempDate = currentDate.withMonth(i);
            for (int k = 1; k <= tempDate.lengthOfMonth(); k++) {
                LocalDate today = LocalDate.of(2023, i, k);
                Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, today);
                if (attendance != null && attendance.getCheckOutTime() == null) {
                    int minute = new Random().nextInt(60);
                    LocalTime checkOutTime = minute >= 30 ? LocalTime.of(17, minute) : LocalTime.of(18, minute);
                    int earlyTime = Math.max(checkOutTime.getMinute() + checkOutTime.getHour() * 60
                            - config.getEndWorkTime().getMinute() - config.getEndWorkTime().getHour() * 60, 0);
                    attendance.setCheckOutTime(checkOutTime);
                    attendance.setEarlyMinutes(earlyTime);
                    LocalTime punchTimeEarly = checkOutTime.minusHours(config.getStartWorkTime()
                            .getHour()).minusMinutes(config.getStartWorkTime().getMinute());
                    LocalTime punchTimeLate = checkOutTime.minusHours(attendance.getCheckInTime()
                            .getHour()).minusMinutes(attendance.getCheckInTime().getMinute());
                    LocalTime punchTime = (attendance.getCheckInTime().getMinute() < config.getStartWorkTime().getMinute()) ? punchTimeEarly : punchTimeLate;
                    double breakHour = config.getBreakTime().getHour() + config.getBreakTime().getMinute() / 60.0;
                    double punchHour = punchTime.getHour() + punchTime.getMinute() / 60.0 - breakHour;
                    attendance.setPunchHour(punchHour);
                    attendanceRepository.save(attendance);
                }
            }
        }
    }
}
