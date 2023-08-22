package com.hai.employeemanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private LocalTime breakTime;

    public AttendanceConfig(Long id, Double workingDaysOfWeek, LocalTime startWorkTime, LocalTime endWorkTime, LocalTime lateAndEarlyTime) {
        this.id = id;
        this.workingDaysOfWeek = workingDaysOfWeek;
        this.startWorkTime = startWorkTime;
        this.endWorkTime = endWorkTime;
        this.lateAndEarlyTime = lateAndEarlyTime;
    }
}
