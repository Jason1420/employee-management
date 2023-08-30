package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.EmployeeDTO;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.help.DeletedEmployee;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmployeeConverter {
    public Employee toEntity(EmployeeDTO dto) {
        return new Employee(dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getGender(),
                dto.getDateOfBirth(),
                dto.getPhoneNumber(),
                dto.getCode());
    }

    public Employee toEntity(EmployeeDTO dto, Employee entity) {
        return new Employee(entity.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getGender(),
                dto.getDateOfBirth(),
                dto.getPhoneNumber(),
                dto.getCode());
    }

    public EmployeeDTO toDto(Employee entity) {
        return new EmployeeDTO(entity.getId(),
                entity.getCode(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getGender(),
                entity.getDateOfBirth(),
                entity.getPhoneNumber());
    }
    public DeletedEmployee toDeleted(Employee entity){
        return new DeletedEmployee(entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getGender(),
                entity.getDateOfBirth(),
                entity.getPhoneNumber(),
                LocalDateTime.now());
    }
}
