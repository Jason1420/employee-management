package com.hai.employeemanagement.dto.help;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountAttendanceDTO {
    private Long employeeId;
    private String employeeName;
    private Integer total;
}
