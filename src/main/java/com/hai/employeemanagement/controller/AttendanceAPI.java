package com.hai.employeemanagement.controller;

import com.hai.employeemanagement.dto.AttendanceDTO;
import com.hai.employeemanagement.dto.help.AttendanceViewDTO;
import com.hai.employeemanagement.dto.help.CountAttendanceDTO;
import com.hai.employeemanagement.exception.helper.Result;
import com.hai.employeemanagement.exception.helper.StatusCode;
import com.hai.employeemanagement.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceAPI {
    private final AttendanceService attendanceService;

    @PostMapping("/{id}")
    public Result checkIn(@PathVariable("id") Long employeeId) {
        AttendanceDTO dto = attendanceService.checkIn(employeeId);
        return new Result(true, StatusCode.SUCCESS, "Check in success!", dto);
    }

    @PutMapping("/{id}")
    public Result checkOut(@PathVariable("id") Long employeeId) {
        AttendanceDTO dto = attendanceService.checkOut(employeeId);
        return new Result(true, StatusCode.SUCCESS, "Check out success!", dto);
    }

    @GetMapping("/view")
    public Result viewAttendance(@RequestBody AttendanceViewDTO dto) {
        List<AttendanceDTO> listDTO = attendanceService.viewAttendance(dto);
        return new Result(true, StatusCode.SUCCESS, "Find success!", listDTO);
    }
    @GetMapping("/view/{id}")
    public Result viewAttendanceOfEmployee(@PathVariable("id") Long id,@RequestBody AttendanceViewDTO dto) {
        List<AttendanceDTO> listDTO = attendanceService.viewAttendanceOfEmployee(id,dto);
        return new Result(true, StatusCode.SUCCESS, "Find success!", listDTO);
    }
    @GetMapping("/count")
    public Result countAttendance(@RequestBody AttendanceViewDTO dto) {
        List<List<Object[]>> listDTO = attendanceService.countAttendance(dto);
        return new Result(true, StatusCode.SUCCESS, "Find success!", listDTO);
    }
}
