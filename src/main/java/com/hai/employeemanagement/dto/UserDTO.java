package com.hai.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
