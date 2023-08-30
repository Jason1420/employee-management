package com.hai.employeemanagement.entity;

import com.hai.employeemanagement.entity.help.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private Date dateOfBirth;
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "department_code", referencedColumnName = "code")
    private Department department;
    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(String firstName, String lastName, String email, Gender gender, Date dateOfBirth, String phoneNumber, String code) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.code = code;
    }

    public Employee(Long id, String firstName, String lastName, String email,
                    Gender gender, Date dateOfBirth, String phoneNumber,String code) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.code = code;
    }
}
