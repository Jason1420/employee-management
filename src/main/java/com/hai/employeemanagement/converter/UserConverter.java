package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.UserDTO;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final EmployeeConverter employeeConverter;
    public UserEntity toEntity(UserDTO dto) {
        return new UserEntity(dto.getUsername(),
                dto.getPassword(),
                dto.getEmail(),
                (dto.getEmployee() != null) ?
                        new Employee(dto.getEmployee().getFirstName(),
                                dto.getEmployee().getLastName()) : null);
    }

    public UserDTO toDto(UserEntity entity) {
        return new UserDTO(entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                (entity.getEmployee() != null) ?
                        employeeConverter.toDto(entity.getEmployee()): null);
    }
}
