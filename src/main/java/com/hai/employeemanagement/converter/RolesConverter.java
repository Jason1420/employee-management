package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.DepartmentDTO;
import com.hai.employeemanagement.dto.RoleDTO;
import com.hai.employeemanagement.entity.Department;
import com.hai.employeemanagement.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RolesConverter {

    public RoleDTO toDTO(Role entity) {
        return new RoleDTO(entity.getName());
    }
}
