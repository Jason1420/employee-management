package com.hai.employeemanagement.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Set<RoleDTO> roles;
    private EmployeeDTO employee;

    public UserDTO(Long id, String username, String password, EmployeeDTO employee) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.employee = employee;
    }
}
