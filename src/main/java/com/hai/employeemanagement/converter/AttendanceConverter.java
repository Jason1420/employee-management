package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.AttendanceDTO;
import com.hai.employeemanagement.entity.Attendance;
import org.springframework.stereotype.Component;

@Component
public class AttendanceConverter {
    public Attendance toEntity(AttendanceDTO dto) {
        return new Attendance(dto.getId(),
                dto.getDate(),
                dto.getCheckInTime(),
                dto.getCheckOutTime(),
                dto.getStatus(),
                dto.getEmployeeName(),
                dto.getEmployeeId());
    }

    //    public Attendance toEntity(AttendanceDTO dto, Attendance entity){
//        return new Attendance(entity.getId(),
//                dto.getFirstName(),
//                dto.getLastName(),
//                dto.getEmail(),
//                dto.getGender(),
//                dto.getDateOfBirth(),
//                dto.getPhoneNumber());
//    }
    public AttendanceDTO toDto(Attendance entity) {
        return new AttendanceDTO(entity.getId(),
                entity.getDate(),
                entity.getCheckInTime(),
                entity.getCheckOutTime(),
                entity.getStatus(),
                entity.getEmployeeName(),
                entity.getEmployeeId(),
                entity.getLateMinutes(),
                entity.getEarlyMinutes());
    }
}
