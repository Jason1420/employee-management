package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query("SELECT a FROM Attendance a WHERE a.employeeId=?1 AND a.date=?2")
    Attendance findByEmployeeIdAndDate(Long employeeId, LocalDate now);

    @Query("SELECT a FROM Attendance a WHERE a.date BETWEEN ?1 AND ?2")
    List<Attendance> findAllBetweenDate(LocalDate start, LocalDate end);

    @Query("SELECT a FROM Attendance a WHERE (a.date BETWEEN ?1 AND ?2) AND a.employeeId = ?3")
    List<Attendance> findAllBetweenDateOfEmployee(LocalDate startDate, LocalDate endDate, Long employeeId);

    @Query("SELECT a.employeeId,a.employeeName, COUNT(a) FROM Attendance a " +
            "WHERE a.date BETWEEN ?1 AND ?2 " +
            "GROUP BY a.employeeId")
    List<List<Object[]>> countAttendance(LocalDate startDate, LocalDate endDate);
}
