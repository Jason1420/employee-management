package com.hai.employeemanagement.service;

import com.hai.employeemanagement.entity.Designation;
import com.hai.employeemanagement.entity.Quarter;
import com.hai.employeemanagement.repository.DesignationRepository;
import com.hai.employeemanagement.repository.QuarterRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class DesignationService {
    private final DesignationRepository designationRepository;

    public Page<Designation> showAllDepartmentPagination(int offset, int size) {
        return designationRepository.findAll(PageRequest.of(offset - 1, size));
    }

    public void addNew(Designation newDesignation) {
        designationRepository.save(newDesignation);
    }

    public void update(Long id, Designation designation) {
        Designation departmentOld = designationRepository.findOneById(id);
        designation.setId(departmentOld.getId());
        designationRepository.save(designation);
    }
}
