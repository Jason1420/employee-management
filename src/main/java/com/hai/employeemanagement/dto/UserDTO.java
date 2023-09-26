package com.hai.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private List<String> roles;
    private EmployeeDTO employee;

    public UserDTO( String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public UserDTO(Long id, String username, String password, EmployeeDTO employee) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.employee = employee;
    }
}
