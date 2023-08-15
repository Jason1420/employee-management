package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.EmployeeDTO;
import com.hai.employeemanagement.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {
    public Employee toEntity(EmployeeDTO dto) {
        return new Employee(dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getGender(),
                dto.getDateOfBirth(),
                dto.getPhoneNumber());
    }

    public Employee toEntity(EmployeeDTO dto, Employee entity) {
        return new Employee(entity.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getGender(),
                dto.getDateOfBirth(),
                dto.getPhoneNumber());
    }

    public EmployeeDTO toDto(Employee entity) {
        return new EmployeeDTO(entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getGender(),
                entity.getDateOfBirth(),
                entity.getPhoneNumber());
    }
}
