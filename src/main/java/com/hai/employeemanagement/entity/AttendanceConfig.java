package com.hai.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "attendance_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceConfig {
    @Id
    private Long id;
    private Double workingDaysOfWeek;
    private LocalTime startWorkTime;
    private LocalTime endWorkTime;
    private LocalTime lateAndEarlyTime;
}
