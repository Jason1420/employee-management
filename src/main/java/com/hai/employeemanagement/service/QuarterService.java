package com.hai.employeemanagement.service;

import com.hai.employeemanagement.entity.Department;
import com.hai.employeemanagement.entity.Quarter;
import com.hai.employeemanagement.repository.DepartmentRepository;
import com.hai.employeemanagement.repository.QuarterRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class QuarterService {
    private final QuarterRepository quarterRepository;

    public Page<Quarter> showAllDepartmentPagination(int offset, int size) {
        return quarterRepository.findAll(PageRequest.of(offset - 1, size));
    }

    public void addNew(Quarter newQuarter) {
        quarterRepository.save(newQuarter);
    }

    public void update(Long id, Quarter quarter) {
        Quarter departmentOld = quarterRepository.findOneById(id);
        quarter.setId(departmentOld.getId());
        quarterRepository.save(quarter);
    }
}
