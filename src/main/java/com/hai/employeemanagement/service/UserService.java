package com.hai.employeemanagement.service;

import com.hai.employeemanagement.converter.DepartmentConverter;
import com.hai.employeemanagement.converter.DesignationConverter;
import com.hai.employeemanagement.converter.QuarterConverter;
import com.hai.employeemanagement.converter.UserConverter;
import com.hai.employeemanagement.dto.*;
import com.hai.employeemanagement.dto.help.ChangePasswordDTO;
import com.hai.employeemanagement.dto.help.DefaultDataDTO;
import com.hai.employeemanagement.dto.help.NewEmployeeDTO;
import com.hai.employeemanagement.entity.*;
import com.hai.employeemanagement.entity.help.Gender;
import com.hai.employeemanagement.exception.Exception400;
import com.hai.employeemanagement.exception.Exception409;
import com.hai.employeemanagement.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final DesignationRepository designationRepository;
    private final QuarterRepository quarterRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationConverter designationConverter;
    private final QuarterConverter quarterConverter;
    private final DepartmentConverter departmentConverter;



    public RoleDTO addNewRole(String role) {
        if (roleRepository.findByName(role) != null) {
            throw new Exception409("This role already exist");
        }
        Role savedEntity = roleRepository.save(new Role(role));
        return new RoleDTO(savedEntity.getId(), role);
    }

//    public UserDTO addUser(UserDTO dto) {
//        UserEntity userEntity = userConverter.toEntity(dto);
//        if (userRepository.findOneByUsername(dto.getUsername()) != null) {
//            throw new Exception409("This username already exists!");
//        }
//        if (employeeRepository.findOneById(dto.getEmployee().getId()) != null) {
//            throw new Exception409("This employee already exists!");
//        }
//        UserEntity entity = userConverter.toEntity(dto);
//        entity.setRoles(roleRepository.findAllByName("EMPLOYEE"));
//        employeeRepository.save(entity.getEmployee());
//        UserEntity savedEntity = userRepository.save(entity);
//        UserDTO returnDTO = userConverter.toDto(savedEntity);
//        return returnDTO;
//    }

//    public UserDTO updateRoleToUser(Long id, String[] roles) {
//        UserEntity userEntity = userRepository.findOneById(id);
//        Set<Role> listRole = new HashSet<>();
//        for (String r : roles) {
//            Role role = roleRepository.findOneByName(r);
//            if (role != null) {
//                listRole.add(role);
//            }
//        }
//        userEntity.setRoles(listRole);
//        UserEntity savedEntity = userRepository.save(userEntity);
//        return userConverter.toDto(savedEntity);
//    }

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
        if (dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            user.setChangedPassword(true);
        } else {
            throw new Exception400("Confirm password and new password must be the same!");
        }

    }

    public String updateEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "successfully";
    }

    public boolean checkPassword(UserEntity entity, String currentPassword) {
        return passwordEncoder.matches(currentPassword, entity.getPassword());
    }

    public Page<UserEntity> showAllUserPagination(int offset, int size) {
        return userRepository.findAll(PageRequest.of(offset - 1, size));
    }

    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }


    public DefaultDataDTO loadDefaultData() {
        //get data from database
        List<Department> listDepartment =  departmentRepository.findAll();
        List<Designation> listDesignation =  designationRepository.findAll();
        List<Quarter> listQuarter =  quarterRepository.findAll();
        // convert to DTO
        List<DepartmentDTO> listDepartmentDTO = listDepartment.stream()
                .map(departmentConverter::toDTO).collect(Collectors.toList());
        List<DesignationDTO> listDesignationDTO = listDesignation.stream()
                . map(designationConverter::toDTO).collect(Collectors.toList());
        List<QuarterDTO> listQuarterDTO =listQuarter.stream()
                .map(quarterConverter::toDTO).collect(Collectors.toList());

        return new DefaultDataDTO(listDesignationDTO,listQuarterDTO,listDepartmentDTO);
    }

    public UserDTO createUser(NewEmployeeDTO newEmployeeDTO) {
        if (userRepository.findOneByUsername(newEmployeeDTO.getUsername()) != null) {
            throw new Exception409("This username already exists!");
        }
        if (employeeRepository.findOneByCode(newEmployeeDTO.getCode()) != null) {
            throw new Exception409("This employee already exists!");
        }
        UserEntity newUser = new UserEntity(newEmployeeDTO.getUsername());
        newUser.setRoles(roleRepository.findAllByName("EMPLOYEE"));
        newUser.setPassword(passwordEncoder.encode(newEmployeeDTO.getPassword()));
        Employee newEmployee = new Employee(newEmployeeDTO.getCode(),
                newEmployeeDTO.getFirstName(),
                newEmployeeDTO.getLastName(),
                newEmployeeDTO.getEmail(),
                Gender.valueOf(newEmployeeDTO.getGender().toUpperCase()),
                newEmployeeDTO.getDateOfBirth(),
                newEmployeeDTO.getPhoneNumber(),
                newEmployeeDTO.getJoiningDate(),
                newEmployeeDTO.getDesignation(),
                newEmployeeDTO.getQuarter(),
                departmentRepository.findOneByName(newEmployeeDTO.getDepartment()));
        newUser.setEmployee(newEmployee);
        employeeRepository.save(newEmployee);
        UserEntity savedEntity = userRepository.save(newUser);
        UserDTO returnDTO = userConverter.toDto(savedEntity);
        returnDTO.setPassword("PROTECTED");
        return returnDTO;
    }
}
