package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.DepartmentDTO;
import com.hai.employeemanagement.dto.DesignationDTO;
import com.hai.employeemanagement.entity.Department;
import com.hai.employeemanagement.entity.Designation;
import org.springframework.stereotype.Component;

@Component
public class DesignationConverter {

    public DesignationDTO toDTO(Designation entity) {
        if(entity != null){

        return new DesignationDTO(entity.getId(),
                entity.getName());
        }else return null;
    }
}
