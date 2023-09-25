package com.hai.employeemanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.help.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String code;
    private String name;

}
