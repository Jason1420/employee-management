package com.hai.employeemanagement.dto;

import com.hai.employeemanagement.entity.help.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class AttendanceDTO {
    private Long id;
    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private Status status;
    private String employeeName;
    private Long employeeId;
    private int lateMinutes;
    private int earlyMinutes;


    public AttendanceDTO(Long id, LocalDate date, LocalTime checkInTime, LocalTime checkOutTime, Status status, String employeeName, Long employeeId, int lateMinutes) {
        this.id = id;
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.status = status;
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.lateMinutes = lateMinutes;
    }

}
