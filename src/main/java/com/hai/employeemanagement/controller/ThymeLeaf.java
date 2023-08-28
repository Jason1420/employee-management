package com.hai.employeemanagement.controller;

import com.hai.employeemanagement.converter.EmployeeConverter;
import com.hai.employeemanagement.converter.UserConverter;
import com.hai.employeemanagement.dto.help.AttendanceViewDTO;
import com.hai.employeemanagement.dto.help.ChangePasswordDTO;
import com.hai.employeemanagement.entity.*;
import com.hai.employeemanagement.entity.help.DeletedEmployee;
import com.hai.employeemanagement.repository.*;
import com.hai.employeemanagement.service.AttendanceService;
import com.hai.employeemanagement.service.DepartmentService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ThymeLeaf {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;
    private final EmployeeConverter employeeConverter;
    private final DeletedEmployeeRepository deletedEmployeeRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;
    private final AttendanceService attendanceService;
    private final DepartmentService departmentService;

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
        UserEntity user = new UserEntity();
        Employee employee = new Employee();
        model.addAttribute("user", user);
        model.addAttribute("employee", employee);
        List<Role> listRole = roleRepository.findAll();
        model.addAttribute("listRole", listRole);
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
        model.addAttribute("newUser", new UserEntity());
        return "find-all-user";
    }

    @PostMapping("/save-employee")
    public String saveEmployee(@ModelAttribute("user") UserEntity user,
                               @ModelAttribute("employee") Employee employee,
                               @RequestParam String selectedRole) {
        userService.addUser(user, employee, selectedRole);
        return "redirect:/employee/list/1/8";
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
        List<Department> listDepartment = departmentRepository.findAll();
        model.addAttribute("listDepartment", listDepartment);
        return "update-employee";
    }

    @PostMapping("/update-employee")
    public String processingUpdateEmployee(@ModelAttribute("employee") Employee employee,
                                           @RequestParam List<String> selectedRoles,
                                           @RequestParam Long department) {
        employee.setDepartment(departmentRepository.findOneById(department));
        userService.updateEmployee(employee);
        UserEntity user = userRepository.findOneByEmployeeId(employee.getId());
        String[] str = new String[selectedRoles.size()];
        selectedRoles.toArray(str);
        if (user != null) {
            userService.updateRoleToUser(user.getId(), str);
        }
        return "redirect:/employee/list/1/8";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        UserEntity user = userRepository.findOneById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update-user")
    public String processingUpdateUser(@ModelAttribute("newUser") UserEntity newUser) {
        userService.updateUser(newUser);
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
    public String createAccount(@PathVariable("id") Long id, Model model) {
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
        userService.addUser(user, eId, selectedRole);
        return "redirect:/updateEmployee/" + user.getEmployee().getId();
    }

    @GetMapping("/attendance")
    public String attendance(Model model, @RequestParam(required = false) Integer month,
                             @RequestParam(required = false) Integer year) {
        LocalDate currentDate = LocalDate.now();
        if (month != null && month > 0 && month <= 12) {
            currentDate = currentDate.withMonth(month);
        }

        if (year != null && year > 0) {
            currentDate = currentDate.withYear(year);
        }
        LocalDate start = currentDate.withDayOfMonth(1);
        LocalDate end = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        List<Employee> listEmployee = employeeRepository.findAll();
        List<Attendance> listAttendance = attendanceService.viewAttendance(new AttendanceViewDTO(start, end));
        Map<Employee, List<Attendance>> result = listEmployee.stream()
                .collect(Collectors.toMap(
                        employee -> employee,
                        employee -> listAttendance.stream()
                                .filter(data -> data.getEmployeeId() == employee.getId())
                                .map(data -> {
                                    double punch = 0.0;
                                    if (data.getPunchHour() != null) {
                                        punch = (double) (Math.ceil(data.getPunchHour() * 100) / 100);
                                    }
                                    data.setPunchHour(punch);
                                    return data;
                                })
                                .collect(Collectors.toList())
                ));
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("listResult", result);
        return "attendance2";
    }

    @GetMapping("/markAttendance")
    public String markAttendance(Model model, @RequestParam(required = false) Integer month,
                                 @RequestParam(required = false) Integer year) {
        UserEntity user = userRepository.findOneByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
        Employee employee = employeeRepository.findOneById(user.getEmployee().getId());
        model.addAttribute("employee", employee);
        LocalDate currentDate = LocalDate.now();
        if (month != null && month > 0 && month <= 12) {
            currentDate = currentDate.withMonth(month);
        }

        if (year != null && year > 0) {
            currentDate = currentDate.withYear(year);
        }
        LocalDate start = currentDate.withDayOfMonth(1);
        LocalDate end = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        List<Attendance> listAttendance = attendanceService.viewAttendanceOfEmployee(employee.getId(),
                new AttendanceViewDTO(start, end));
        model.addAttribute("listAttendance", listAttendance);
        Attendance todayAttendance = attendanceService.viewAttendanceOfEmployeeToday(employee.getId());
        model.addAttribute("todayAttendance", todayAttendance);
        return "mark-attendance";
    }

    @PostMapping("/checkIn/{id}")
    public String checkIn(@PathVariable("id") Long employeeId) {
        attendanceService.checkIn(employeeId);
        return "redirect:/markAttendance";
    }

    @PostMapping("/checkOut/{id}")
    public String checkOut(@PathVariable("id") Long employeeId) {
        attendanceService.checkOut(employeeId);
        return "redirect:/markAttendance";
    }

    @GetMapping("/department/list/{offset}/{size}")
    public String findAllDepartment(@PathVariable("offset") int offset, @PathVariable("size") int size, Model model) {
        Page<Department> list = departmentService.showAllDepartmentPagination(offset, size);
        model.addAttribute("newDepartment", new Department());
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", offset);
        model.addAttribute("size", size);
        model.addAttribute("listDepartment", list);
        return "find-all-department";
    }

    @PostMapping("/new-department")
    public String newDepartment(@ModelAttribute("newDepartment") Department newDepartment) {
        departmentService.addNew(newDepartment);
        return "redirect:/department/list/1/10";
    }

    @PostMapping("/update-department/{id}")
    public String updateDepartment(@PathVariable("id") Long id, @ModelAttribute("newDepartment") Department department) {
        departmentService.update(id, department);
        return "redirect:/department/list/1/10";
    }

    @GetMapping("/test")
    public String test() {
        return "/header-footer/header2";
    }
}
