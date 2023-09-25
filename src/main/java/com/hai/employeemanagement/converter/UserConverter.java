package com.hai.employeemanagement.converter;

import com.hai.employeemanagement.dto.EmployeeDTO;
import com.hai.employeemanagement.dto.UserDTO;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final EmployeeConverter employeeConverter;
    private final RolesConverter rolesConverter;
    public UserEntity toEntity(UserDTO dto) {
        return new UserEntity(dto.getUsername(),
                dto.getPassword());
    }

//    public UserDTO toDto(UserEntity entity) {
//        return new UserDTO(entity.getId(),
//                entity.getUsername(),
//                entity.getPassword(),
//                (entity.getEmployee() != null) ?
//                        employeeConverter.toDto(entity.getEmployee()) : null);
//    }

    public UserDTO toDtoAfterLogin (UserEntity userEntity){
        Employee employee = userEntity.getEmployee();

        return new UserDTO(userEntity.getUsername(),
                userEntity.getRoles().stream().map(role -> {
                    return role.getName();
                }).collect(Collectors.toList())
                );
    }
}
