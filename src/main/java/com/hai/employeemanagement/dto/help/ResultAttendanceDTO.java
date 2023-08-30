package com.hai.employeemanagement.dto.help;

import com.hai.employeemanagement.entity.help.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultAttendanceDTO {
    private LocalDate date;
    private Long employeeId;
    private LocalTime checkInTime;
    private int lateMinutes;
    private LocalTime checkOutTime;
    private int earlyMinutes;
    private Double punchHour;
    private Status status;
}
