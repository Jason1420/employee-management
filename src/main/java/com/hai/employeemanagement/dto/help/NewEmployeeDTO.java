package com.hai.employeemanagement.dto.help;

import com.hai.employeemanagement.dto.DepartmentDTO;
import com.hai.employeemanagement.entity.help.Gender;
import com.hai.employeemanagement.entity.help.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEmployeeDTO {
    private String code;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String gender;
    private Date dateOfBirth;
    private String phoneNumber;
    private Date joiningDate;
    private String designation;
    private String quarter;
    private String department;
}
