package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.EmployeeDTO;
import com.hai.employeemanagement.dto.help.RegisterDTO;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.UserEntity;
import com.hai.employeemanagement.entity.help.DeletedEmployee;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;

@Component
public class RegisterConverter {
    public UserEntity toUser(RegisterDTO dto){
        return new UserEntity(dto.getUsername(), dto.getPassword());
    }
    public Employee toEmployee(RegisterDTO dto){
        return new Employee(dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getGender(),
                Date.valueOf(dto.getDateOfBirth()),
                dto.getPhoneNumber(),
                dto.getCode());
    }
}
