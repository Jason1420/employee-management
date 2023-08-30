package com.hai.employeemanagement.entity.help;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "deleted_employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletedEmployee {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private Date dateOfBirth;
    private String phoneNumber;
    private LocalDateTime deletedDate;

    public DeletedEmployee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public DeletedEmployee(String firstName, String lastName, String email, Gender gender, Date dateOfBirth, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }
}
