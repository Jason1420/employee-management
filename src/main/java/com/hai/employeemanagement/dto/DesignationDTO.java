package com.hai.employeemanagement.dto;

import com.hai.employeemanagement.entity.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignationDTO {

    private Long id;
    private String name;
}
