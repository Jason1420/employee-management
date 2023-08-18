package com.hai.employeemanagement.service;

import com.hai.employeemanagement.converter.UserConverter;
import com.hai.employeemanagement.dto.RoleDTO;
import com.hai.employeemanagement.dto.UserDTO;
import com.hai.employeemanagement.dto.help.ChangePasswordDTO;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.Role;
import com.hai.employeemanagement.entity.UserEntity;
import com.hai.employeemanagement.exception.Exception400;
import com.hai.employeemanagement.exception.Exception409;
import com.hai.employeemanagement.repository.EmployeeRepository;
import com.hai.employeemanagement.repository.RoleRepository;
import com.hai.employeemanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public RoleDTO addNewRole(String role) {
        if (roleRepository.findByName(role) != null) {
            throw new Exception409("This role already exist");
        }
        Role savedEntity = roleRepository.save(new Role(role));
        return new RoleDTO(savedEntity.getId(), role);
    }

    public UserDTO addUser(UserDTO dto) {
        UserEntity userEntity = userConverter.toEntity(dto);
        if (userRepository.findOneByUsername(dto.getUsername()) != null) {
            throw new Exception409("This username already exists!");
        }
        if (employeeRepository.findOneById(dto.getEmployee().getId()) != null) {
            throw new Exception409("This employee already exists!");
        }
        UserEntity entity = userConverter.toEntity(dto);
        entity.setRoles(roleRepository.findAllByName("EMPLOYEE"));
        employeeRepository.save(entity.getEmployee());
        UserEntity savedEntity = userRepository.save(entity);
        UserDTO returnDTO = userConverter.toDto(savedEntity);
        return returnDTO;
    }
    public String addUser(UserEntity user, Employee employee) {
        if (userRepository.findOneByUsername(user.getUsername()) != null) {
            throw new Exception409("This username already exists!");
        }
        if (employeeRepository.findOneByEmail(employee.getEmail()) != null) {
            throw new Exception409("This email already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findAllByName("EMPLOYEE"));
        user.setEmployee(employee);
        employeeRepository.save(employee);
        UserEntity savedEntity = userRepository.save(user);
        return "successfully";
    }

    public UserDTO updateRoleToUser(Long id, String[] roles) {
        UserEntity userEntity = userRepository.findOneById(id);
        Set<Role> listRole = new HashSet<>();
        for (String r : roles) {
            Role role = roleRepository.findOneByName(r);
            if (role != null) {
                listRole.add(role);
            }
        }
        userEntity.setRoles(listRole);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userConverter.toDto(savedEntity);
    }

    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findOneById(id);
        userEntity.setLocked(true);
    }

    public void changePassword(UserEntity user, ChangePasswordDTO dto) {
        if (user == null) {
            throw new EntityNotFoundException("This user is not found!");
        }
        if (!checkPassword(user, dto.getCurrentPassword())) {
            throw new Exception400("Wrong current password!");
        }
        if(dto.getNewPassword().equals(dto.getConfirmNewPassword())){
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            user.setChangedPassword(true);
        }else{
            throw new Exception400("Confirm password and new password must be the same!");
        }

    }

    public String updateEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "successfully";
    }
    public boolean checkPassword(UserEntity entity, String currentPassword) {
        return passwordEncoder.matches(currentPassword,entity.getPassword());
    }
}
