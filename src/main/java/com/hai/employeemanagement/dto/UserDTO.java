package com.hai.employeemanagement.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<RoleDTO> roles;
    private EmployeeDTO employee;

    public UserDTO(String username, String password, String email, EmployeeDTO employee) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.employee = employee;
    }
}
