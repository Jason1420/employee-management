package com.hai.employeemanagement.dto.help;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllAttendanceDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
