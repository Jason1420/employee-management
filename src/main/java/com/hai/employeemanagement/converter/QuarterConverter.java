package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.DesignationDTO;
import com.hai.employeemanagement.dto.QuarterDTO;
import com.hai.employeemanagement.entity.Designation;
import com.hai.employeemanagement.entity.Quarter;
import org.springframework.stereotype.Component;

@Component
public class QuarterConverter {

    public QuarterDTO toDTO(Quarter entity) {
        if(entity != null){

        return new QuarterDTO(entity.getId(),
                entity.getName());
        }else return null;
    }
}
