package com.hai.employeemanagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class AttendanceConfigDTO {
    private Long id;
    private Double workingDaysOfWeek;
    private LocalTime startWorkTime;
    private LocalTime endWorkTime;
    private LocalTime lateAndEarlyTime;
}
