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
    private LocalTime breakTime;

    public AttendanceConfigDTO(Long id, Double workingDaysOfWeek, LocalTime startWorkTime, LocalTime endWorkTime, LocalTime lateAndEarlyTime) {
        this.id = id;
        this.workingDaysOfWeek = workingDaysOfWeek;
        this.startWorkTime = startWorkTime;
        this.endWorkTime = endWorkTime;
        this.lateAndEarlyTime = lateAndEarlyTime;
    }
}
