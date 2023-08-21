package com.hai.employeemanagement.service;

import com.hai.employeemanagement.converter.EmployeeConverter;
import com.hai.employeemanagement.dto.EmployeeDTO;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;

    public EmployeeDTO viewProfile(Long id) {
        Employee employee = employeeRepository.findOneById(id);
        return employeeConverter.toDto(employee);
    }

    public List<EmployeeDTO> viewAllEmployee() {
        List<Employee> list = employeeRepository.findAll();
        return list.stream().map(employeeConverter::toDto).collect(Collectors.toList());
    }

    public EmployeeDTO updateInformation(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findOneById(id);
        Employee updatedEmployee = employeeConverter.toEntity(employeeDTO, employee);
        employeeRepository.save(updatedEmployee);
        return employeeConverter.toDto(updatedEmployee);
    }
    public Page<Employee> showAllEmployeePagination(int offset, int size) {
        return employeeRepository.findAll(PageRequest.of(offset - 1, size));
// convert to DTO
        // use Pageable
    }
}
