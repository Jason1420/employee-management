package com.hai.employeemanagement.controller;

import com.hai.employeemanagement.dto.UserDTO;
import com.hai.employeemanagement.entity.Employee;
import com.hai.employeemanagement.entity.UserEntity;
import com.hai.employeemanagement.exception.helper.Result;
import com.hai.employeemanagement.exception.helper.StatusCode;
import com.hai.employeemanagement.jwt.JwtGenerator;
import com.hai.employeemanagement.repository.EmployeeRepository;
import com.hai.employeemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ThymeLeaf {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;
    @RequestMapping("/")
    public String cssTest( Model model){
        model.addAttribute("user",SecurityContextHolder.getContext().getAuthentication());
        return "index";
    }

    @RequestMapping("/login")
    public String showLoginPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String showLogin(){
        return"redirect:/login?logout";
    }
    @GetMapping("/employee/list")
    public String findAllEmployee(Model model){

        model.addAttribute("listEmployee",employeeRepository.findAll());
        return "find-all-employee";
    }
    @GetMapping("/admin/user")
    public String addUser(Model model) {
        UserEntity user = new UserEntity();
        Employee employee = new Employee();
        model.addAttribute("user",user);
        model.addAttribute("employee",employee);
        return "new-employee";
    }
    @PostMapping("/save-employee")
    public String saveEmployee(@ModelAttribute("user") UserEntity user,
                               @ModelAttribute("employee") Employee employee) {
        userService.addUser(user, employee);
        return "redirect:/";
    }
    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable("id")Long id, Model model) {
        Employee employee = employeeRepository.findOneById(id);
        model.addAttribute("employee",employee);
        return "update-employee";
    }
    @PostMapping("/update-employee")
    public String updateEmployee(@ModelAttribute("employee") Employee employee) {
        userService.updateEmployee(employee);
        return "redirect:/employee/list";
    }
}
