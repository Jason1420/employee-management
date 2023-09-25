package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.DepartmentDTO;
import com.hai.employeemanagement.dto.EmployeeDTO;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.help.DeletedEmployee;
import com.hai.employeemanagement.entity.help.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EmployeeConverter {
    private final DepartmentConverter departmentConverter;
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

    public DeletedEmployee toDeleted(Employee entity) {
        return new DeletedEmployee(entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getGender(),
                entity.getDateOfBirth(),
                entity.getPhoneNumber(),
                LocalDateTime.now());
    }
    public EmployeeDTO toDtoAfterLogin(Employee entity) {
        return new EmployeeDTO(entity.getId(),
                entity.getCode(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getGender(),
                entity.getDateOfBirth(),
                entity.getPhoneNumber(),
                entity.getJoiningDate(),
                entity.getDesignation(),
                entity.getQuarter(),
                entity.getAvatar(),
                entity.getSalary(),
                departmentConverter.toDTO(entity.getDepartment()));
    }
}
