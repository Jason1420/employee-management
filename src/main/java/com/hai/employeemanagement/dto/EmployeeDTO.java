package com.hai.employeemanagement.dto;

import com.hai.employeemanagement.entity.help.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String code;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private Date dateOfBirth;
    private String phoneNumber;
    private Date joiningDate;
    private String designation;
    private String quarter;
    private String avatar;
    private Long salary;
    private DepartmentDTO department;
    public EmployeeDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public EmployeeDTO(Long id, String code, String firstName, String lastName, String email, Gender gender, Date dateOfBirth, String phoneNumber) {
        this.id = id;
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public EmployeeDTO(Long id, String code, String firstName, String lastName, String email, Gender gender,
                       Date dateOfBirth, String phoneNumber, Date joiningDate, String designation,
                       String quarter, String avatar, Long salary, DepartmentDTO department) {
        this.id = id;
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.joiningDate = joiningDate;
        this.designation = designation;
        this.quarter = quarter;
        this.avatar = avatar;
        this.salary = salary;
        this.department = department;
    }
}
