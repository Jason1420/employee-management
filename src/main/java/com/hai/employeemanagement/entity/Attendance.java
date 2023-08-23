package com.hai.employeemanagement.entity;

import com.hai.employeemanagement.entity.help.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private Status status;
    private String employeeName;
    private Long employeeId;
    private int lateMinutes;
    private int earlyMinutes;
    private Double punchHour;

    public Attendance(LocalDate date, LocalTime checkInTime, Status status, String employeeName, Long employeeId, int lateMinutes) {
        this.date = date;
        this.checkInTime = checkInTime;
        this.status = status;
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.lateMinutes = lateMinutes;
    }

    public Attendance(Long id, LocalDate date, LocalTime checkInTime, LocalTime checkOutTime, Status status, String employeeName, Long employeeId) {
        this.id = id;
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.status = status;
        this.employeeName = employeeName;
        this.employeeId = employeeId;
    }

}
