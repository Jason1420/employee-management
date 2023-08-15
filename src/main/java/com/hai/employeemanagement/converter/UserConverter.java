package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.EmployeeDTO;
import com.hai.employeemanagement.dto.UserDTO;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User toEntity(UserDTO dto) {
        return new User(dto.getUsername(),
                dto.getPassword(),
                dto.getEmail(),
                (dto.getEmployee() != null) ?
                        new Employee(dto.getEmployee().getFirstName(),
                                dto.getEmployee().getLastName()) : null);
    }

    public UserDTO toDto(User entity) {
        return new UserDTO(entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                (entity.getEmployee() != null) ?
                        new EmployeeDTO(entity.getEmployee().getFirstName(),
                                entity.getEmployee().getLastName()) : null);
    }
}
