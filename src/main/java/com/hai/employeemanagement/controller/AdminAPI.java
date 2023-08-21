package com.hai.employeemanagement.controller;

import com.hai.employeemanagement.dto.AttendanceConfigDTO;
import com.hai.employeemanagement.dto.RoleDTO;
import com.hai.employeemanagement.dto.UserDTO;
import com.hai.employeemanagement.exception.helper.Result;
import com.hai.employeemanagement.exception.helper.StatusCode;
import com.hai.employeemanagement.service.AttendanceConfigService;
import com.hai.employeemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAPI {
    private final UserService userService;
    private final AttendanceConfigService attendanceConfigService;

    @PostMapping("/user")
    public Result addUser(@RequestBody UserDTO dto) {
        UserDTO savedDTO = userService.addUser(dto);
        return new Result(true, StatusCode.SUCCESS, "Add user success", savedDTO);
    }

    @PostMapping("/role")
    public Result addRole(@RequestBody String name) {
        RoleDTO savedDTO = userService.addNewRole(name);
        return new Result(true, StatusCode.SUCCESS, "Add role success", savedDTO);
    }

    @PutMapping("/user/{id}")
    public Result updateRoleToUser(@PathVariable("id") Long id, @RequestBody String[] roles) {
        UserDTO updatedDTO = userService.updateRoleToUser(id, roles);
        return new Result(true, StatusCode.SUCCESS, "Update role success", updatedDTO);
    }

    @DeleteMapping("/user/{id}")
    public Result deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new Result(true, StatusCode.SUCCESS, "Delete user success");
    }

    @PostMapping("/attendance")
    public Result configAttendance(@RequestBody AttendanceConfigDTO dto) {
        AttendanceConfigDTO config = attendanceConfigService.configAttendance(dto);
        return new Result(true, StatusCode.SUCCESS, "Config attendance success", config);
    }
}
