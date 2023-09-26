package com.hai.employeemanagement.dto.help;

import com.hai.employeemanagement.dto.DepartmentDTO;
import com.hai.employeemanagement.dto.DesignationDTO;
import com.hai.employeemanagement.dto.QuarterDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
//this DTO to save default data is going to send to front end
public class DefaultDataDTO {

    private List<DesignationDTO> designations;
    private List<QuarterDTO> quarters;
    private List<DepartmentDTO> departments;

    public DefaultDataDTO(List<DesignationDTO> designations, List<QuarterDTO> quarters, List<DepartmentDTO> departments) {
        this.designations = designations;
        this.quarters = quarters;
        this.departments = departments;
    }
}
