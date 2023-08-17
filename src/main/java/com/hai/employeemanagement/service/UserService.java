package com.hai.employeemanagement.service;

import com.hai.employeemanagement.converter.UserConverter;
import com.hai.employeemanagement.dto.RoleDTO;
import com.hai.employeemanagement.dto.UserDTO;
import com.hai.employeemanagement.dto.help.ChangePasswordDTO;
import com.hai.employeemanagement.entity.Role;
import com.hai.employeemanagement.entity.UserEntity;
import com.hai.employeemanagement.exception.Exception409;
import com.hai.employeemanagement.repository.EmployeeRepository;
import com.hai.employeemanagement.repository.RoleRepository;
import com.hai.employeemanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

    public void changePassword(Long id, ChangePasswordDTO dto) {
        UserEntity entity = userRepository.findOneById(id);
        if (entity == null) {
            throw new EntityNotFoundException("This user is not found!");
        }
//        if (!userConverter.checkPassword(entity, passwordEncoder.encode(dto.getCurrentPassword()))) {
//            throw new Exception400("Wrong current password!");
//        }
        entity.setPassword(dto.getNewPassword());
        entity.setChangedPassword(true);
    }
}
