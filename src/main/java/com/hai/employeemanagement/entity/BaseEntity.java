package com.hai.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;
    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;
    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modifiedDate;
}
