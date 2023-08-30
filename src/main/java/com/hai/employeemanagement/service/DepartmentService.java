package com.hai.employeemanagement.service;

import com.hai.employeemanagement.entity.Department;
import com.hai.employeemanagement.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Page<Department> showAllDepartmentPagination(int offset, int size) {
        return departmentRepository.findAll(PageRequest.of(offset - 1, size));
    }

    public void addNew(Department newDepartment) {
        departmentRepository.save(newDepartment);
    }

    public void update(Long id, Department department) {
        Department departmentOld = departmentRepository.findOneById(id);
        department.setId(departmentOld.getId());
        departmentRepository.save(department);
    }
}
