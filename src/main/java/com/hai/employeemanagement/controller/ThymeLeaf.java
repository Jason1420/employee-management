package com.hai.employeemanagement.controller;

import com.hai.employeemanagement.exception.helper.Result;
import com.hai.employeemanagement.repository.EmployeeRepository;
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
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String cssTest( Model model){
        model.addAttribute("user",SecurityContextHolder.getContext().getAuthentication());
        return "index";
    }
    @GetMapping("/employee/list")
    public String findAllEmployee(Model model){

        model.addAttribute("listEmployee",employeeRepository.findAll());
        return "find-all-employee";
    }
    @GetMapping("/login")
    public String showLoginPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";
    }
    @PostMapping("/login")
    public void login() {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    @GetMapping("/logout")
    public String showLogin(){
        return "redirect:/login?logout";
    }

}
