package com.hai.employeemanagement.repository;

import com.hai.employeemanagement.entity.Designation;
import com.hai.employeemanagement.entity.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignationRepository extends JpaRepository<Designation, Long> {

    Designation findOneById(Long id);

    List<Designation> findAll();

    Designation findOneByName(String designationName);
}
