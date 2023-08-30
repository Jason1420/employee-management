package com.hai.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
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

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserEntity(String username, String password, Employee employee) {
        this.username = username;
        this.password = password;
        this.employee = employee;
    }
}
