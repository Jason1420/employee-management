package com.hai.employeemanagement.controller;

import com.hai.employeemanagement.dto.EmployeeDTO;
import com.hai.employeemanagement.exception.helper.Result;
import com.hai.employeemanagement.exception.helper.StatusCode;
import com.hai.employeemanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeAPI {
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public Result viewProfile(@PathVariable("id") Long id) {
        EmployeeDTO employeeDTO = employeeService.viewProfile(id);
        return new Result(true, StatusCode.SUCCESS, "Find one success", employeeDTO);
    }

    @GetMapping
    public Result viewAllEmployee() {
        List<EmployeeDTO> listDTO = employeeService.viewAllEmployee();
        return new Result(true, StatusCode.SUCCESS, "Find all success", listDTO);
    }

    @PutMapping("/{id}")
    public Result updateInformation(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedDTO = employeeService.updateInformation(id, employeeDTO);
        return new Result(true, StatusCode.SUCCESS, "Update employee success", updatedDTO);
    }
}
