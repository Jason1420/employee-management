package com.hai.employeemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "Department")
@Data
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private String name;
    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private Set<Employee> employees;
    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private Set<Designation> designations;

    public Department(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
