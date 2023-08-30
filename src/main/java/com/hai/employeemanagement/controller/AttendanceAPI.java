package com.hai.employeemanagement.controller;

import com.hai.employeemanagement.dto.help.AttendanceViewDTO;
import com.hai.employeemanagement.dto.help.CountAttendanceDTO;
import com.hai.employeemanagement.entity.Attendance;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.exception.helper.Result;
import com.hai.employeemanagement.exception.helper.StatusCode;
import com.hai.employeemanagement.repository.EmployeeRepository;
import com.hai.employeemanagement.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceAPI {
    private final AttendanceService attendanceService;
    private final EmployeeRepository employeeRepository;

    @PostMapping("/{id}")
    public Result checkIn(@PathVariable("id") Long employeeId) {
        attendanceService.checkInData(employeeId);
        return new Result(true, StatusCode.SUCCESS, "Check in success!");
    }

    @PutMapping("/{id}")
    public Result checkOut(@PathVariable("id") Long employeeId) {
        attendanceService.checkOutData(employeeId);
        return new Result(true, StatusCode.SUCCESS, "Check out success!");
    }

    @GetMapping("/view")
    public Result viewAttendance(@RequestBody AttendanceViewDTO dto) {
        List<Attendance> listAttendance = attendanceService.viewAttendance(dto);
        List<Employee> listEmployee = employeeRepository.findAll();
        Map<Employee, List<Attendance>> result = new HashMap<>();
        for (Employee employee : listEmployee) {
            result.put(employee, listAttendance.stream().filter(data ->
                    data.getEmployeeId() == employee.getId()).collect(Collectors.toList()));
        }
        return new Result(true, StatusCode.SUCCESS, "Find success!", result);
    }

//    @GetMapping("/view/{id}")
//    public Result viewAttendanceOfEmployee(@PathVariable("id") Long id, @RequestBody AttendanceViewDTO dto) {
//        List<AttendanceDTO> listDTO = attendanceService.viewAttendanceOfEmployee(id, dto);
//        return new Result(true, StatusCode.SUCCESS, "Find success!", listDTO);
//    }

    @GetMapping("/count")
    public Result countAttendance(@RequestBody AttendanceViewDTO dto) {
        List<CountAttendanceDTO> listDTO = attendanceService.countAttendance(dto);
        return new Result(true, StatusCode.SUCCESS, "Find success!", listDTO);
    }

    @GetMapping("/export")
    private ResponseEntity<Resource> exportAttendance(@RequestParam("fromDate") LocalDate from,
                                                      @RequestParam("toDate") LocalDate to) throws IOException {
        String fileName = "attendance.xlsx";
        ByteArrayInputStream actualData = attendanceService.getActualData(from, to);
        InputStreamResource file = new InputStreamResource(actualData);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }

    @GetMapping("/count/export")
    private ResponseEntity<Resource> exportCountAttendance(@RequestBody AttendanceViewDTO dto) throws IOException {
        String fileName = "count.xlsx";
        ByteArrayInputStream actualData = attendanceService.getActualData(dto);
        InputStreamResource file = new InputStreamResource(actualData);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }
}
