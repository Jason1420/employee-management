package com.hai.employeemanagement.controller;

import com.hai.employeemanagement.dto.help.ChangePasswordDTO;
import com.hai.employeemanagement.exception.helper.Result;
import com.hai.employeemanagement.exception.helper.StatusCode;
import com.hai.employeemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userService;
//    @PostMapping("/login")
//    public Result login(@RequestBody LoginDTO loginDTO){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtGenerator.generateToken(authentication);
//        return new Result(true, StatusCode.SUCCESS, "Login success", new AuthResponseDTO(token).toString());
//    }

//    @PutMapping("/user/{id}")
//    public Result changPassword(@PathVariable("id") Long id, @RequestBody ChangePasswordDTO dto) {
//        userService.changePassword(id, dto);
//        return new Result(true, StatusCode.SUCCESS, "Change password success!");
//    }

}
