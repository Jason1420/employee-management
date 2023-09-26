package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.DepartmentDTO;
import com.hai.employeemanagement.dto.help.RegisterDTO;
import com.hai.employeemanagement.entity.Department;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class DepartmentConverter {

    public DepartmentDTO toDTO(Department entity) {
        if(entity != null){

        return new DepartmentDTO(entity.getId(),
                entity.getCode(),
                entity.getName());
        }else return null;
    }

    public Department toEntity(DepartmentDTO dto) {
        if(dto != null){

            return new Department(dto.getId(),
                    dto.getCode(),
                    dto.getName());
        }else return null;
    }
}
