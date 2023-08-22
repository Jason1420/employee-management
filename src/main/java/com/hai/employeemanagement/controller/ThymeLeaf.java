package com.hai.employeemanagement.controller;

import com.hai.employeemanagement.converter.EmployeeConverter;
import com.hai.employeemanagement.converter.UserConverter;
import com.hai.employeemanagement.dto.help.ChangePasswordDTO;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.Role;
import com.hai.employeemanagement.entity.UserEntity;
import com.hai.employeemanagement.entity.help.DeletedEmployee;
import com.hai.employeemanagement.repository.DeletedEmployeeRepository;
import com.hai.employeemanagement.repository.EmployeeRepository;
import com.hai.employeemanagement.repository.RoleRepository;
import com.hai.employeemanagement.repository.UserRepository;
import com.hai.employeemanagement.service.EmployeeService;
import com.hai.employeemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ThymeLeaf {
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final EmployeeConverter employeeConverter;
    private final DeletedEmployeeRepository deletedEmployeeRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;

    @RequestMapping("/")
    public String cssTest(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication());
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String showLogin() {
        return "redirect:/login?logout";
    }

    @GetMapping("/employee/list/{offset}/{size}")
    public String findAllEmployee(@PathVariable("offset") int offset, @PathVariable("size") int size, Model model) {
        Page<Employee> list = employeeService.showAllEmployeePagination(offset, size);
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", offset);
        model.addAttribute("size", size);
        model.addAttribute("listEmployee", list);
        return "find-all-employee";
    }
    @GetMapping("/user/list/{offset}/{size}")
    public String findAllUser(@PathVariable("offset") int offset, @PathVariable("size") int size, Model model) {
        Page<UserEntity> list = userService.showAllUserPagination(offset, size);
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", offset);
        model.addAttribute("size", size);
        model.addAttribute("listUser", list);
        return "find-all-user";
    }

    @GetMapping("/admin/user")
    public String addUser(Model model) {
        UserEntity user = new UserEntity();
        Employee employee = new Employee();
        model.addAttribute("user", user);
        model.addAttribute("employee", employee);
        List<Role> listRole = roleRepository.findAll();
        model.addAttribute("listRole", listRole);
        return "new-employee";
    }

    @PostMapping("/save-employee")
    public String saveEmployee(@ModelAttribute("user") UserEntity user,
                               @ModelAttribute("employee") Employee employee,
                               @RequestParam String selectedRole) {
        userService.addUser(user, employee, selectedRole);
        return "redirect:/";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String processingDeleteEmployee(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findOneById(id); // find employee
        UserEntity user = userRepository.findOneByEmployeeId(id); // find user
        if (user != null) {
            String[] role = {""};
            userService.updateRoleToUser(user.getId(), role); // delete role
            userRepository.delete(user); // delete user
        }
        DeletedEmployee deletedEmployee = employeeConverter.toDeleted(employee);// convert employee
        deletedEmployeeRepository.save(deletedEmployee);// save employee to deleted
        employeeRepository.delete(employee); // delete employee
        model.addAttribute("listDeletedEmployee", deletedEmployeeRepository.findAll());
        return "delete-employee";
    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findOneById(id);
        model.addAttribute("employee", employee);
        UserEntity user = userRepository.findOneByEmployeeId(id);
        model.addAttribute("user", user);
        List<Role> listRole = roleRepository.findAll();
        model.addAttribute("listRole", listRole);
        return "update-employee";
    }

    @PostMapping("/update-employee")
    public String processingUpdateEmployee(@ModelAttribute("employee") Employee employee,
                                           @RequestParam List<String> selectedRoles) {
        userService.updateEmployee(employee);
        UserEntity user = userRepository.findOneByEmployeeId(employee.getId());
        String[] str = new String[selectedRoles.size()];
        selectedRoles.toArray(str);
        userService.updateRoleToUser(user.getId(), str);
        return "redirect:/employee/list/1/10";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        UserEntity user = userRepository.findOneByEmployeeId(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update-user")
    public String processingUpdateUser(@ModelAttribute("user") UserEntity user) {
        userService.updateUser(user);
        return "redirect:/user/list/1/10";
    }
    @PostMapping("/update-role")
    public String processingUpdateRoleEmployee(@ModelAttribute("roles") String[] roles,
                                               @RequestParam Long id) {
        userService.updateRoleToUser(id, roles);
        return "redirect:/employee/list/1/5";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        UserEntity user = userRepository.findOneByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
        Employee employee = employeeRepository.findOneById(user.getEmployee().getId());
        model.addAttribute("employee", employee);
        return "profile-employee";
    }

    @GetMapping("/profile/change-password")
    public String changePassword(Model model) {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        model.addAttribute("changePasswordDTO", changePasswordDTO);
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute("changePasswordDTO") ChangePasswordDTO changePasswordDTO) {
        UserEntity user = userRepository.findOneByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
        userService.changePassword(user, changePasswordDTO);
        return "change-password";
    }
    @GetMapping("/createAccount/{id}")
    public String createAccount(@PathVariable("id") Long id ,Model model) {
        UserEntity user = new UserEntity();
        Employee employee = employeeRepository.findOneById(id);
        model.addAttribute("user", user);
        model.addAttribute("employee", employee);
        List<Role> listRole = roleRepository.findAll();
        model.addAttribute("listRole", listRole);
        return "create-account";
    }
    @PostMapping("/create-account")
    public String processingCreateAccount(@ModelAttribute("user") UserEntity user,
                                          @ModelAttribute("employee") Employee employee,
                                          @RequestParam String selectedRole) {
        Long eId = employee.getId();
        userService.addUser(user,eId, selectedRole);
        return "redirect:/updateEmployee/" + user.getEmployee().getId();
    }
    @GetMapping("/attendance")
    public String test(Model model) {
        List<Employee> list = employeeRepository.findAll();
        model.addAttribute("listEmployee", list);
        return "attendance";
    }
    @GetMapping("/markAttendance")
    public String markAttendance(Model model) {
        UserEntity user = userRepository.findOneByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
        Employee employee = employeeRepository.findOneById(user.getEmployee().getId());
        model.addAttribute("employee", employee);
        return "mark-attendance";
    }
}
