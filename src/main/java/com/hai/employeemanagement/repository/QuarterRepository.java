package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.Designation;
import com.hai.employeemanagement.entity.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuarterRepository extends JpaRepository<Quarter, Long> {

    Quarter findOneById(Long id);

    List<Quarter> findAll();

    Quarter findOneByName(String quarterName);
}
