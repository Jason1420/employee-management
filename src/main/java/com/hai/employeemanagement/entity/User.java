package com.hai.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean locked = false;
    private Boolean enabled = false;
    private Boolean changedPassword = false;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public User(String username, String password, String email, Employee employee) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.employee = employee;
    }
}
