package com.hai.employeemanagement.controller;

import com.hai.employeemanagement.dto.UserDTO;
import com.hai.employeemanagement.dto.help.DefaultDataDTO;
import com.hai.employeemanagement.dto.help.NewEmployeeDTO;
import com.hai.employeemanagement.dto.login.AuthResponseDTO;
import com.hai.employeemanagement.dto.login.LoginDTO;
import com.hai.employeemanagement.exception.helper.Result;
import com.hai.employeemanagement.exception.helper.StatusCode;
import com.hai.employeemanagement.jwt.JwtGenerator;
import com.hai.employeemanagement.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthResponseDTO authResponseDTO = jwtGenerator.generateToken(authentication);
        return new Result(true, StatusCode.SUCCESS, "Login success", authResponseDTO);
    }
    @PostMapping("/log-out")
    public Result logout(){
        jwtGenerator.resetCookie();
        return new Result(true, StatusCode.SUCCESS, "logout success");
    }
    @PostMapping("/refreshToken")
    public Result refreshToken(@CookieValue("refreshToken") String refreshToken ){
        AuthResponseDTO authResponseDTO = jwtGenerator.refreshToken(refreshToken);
        return new Result(true, StatusCode.SUCCESS, "Refresh token success",authResponseDTO);
    }
    @GetMapping("/api/v1/data-default")
    public Result getDataDefault(){
        DefaultDataDTO defaultDataDTO = userService.loadDefaultData();
        return new Result(true, StatusCode.SUCCESS, "Load default data success",defaultDataDTO);
    }

    @PostMapping("/user")
    public Result createUser(@RequestBody NewEmployeeDTO newEmployeeDTO){
        UserDTO savedUser = userService.createUser(newEmployeeDTO);
        return new Result(true, StatusCode.SUCCESS, "Load default data success",savedUser);
    }

//    @GetMapping("/getCookie")
//    public String getCookie(@CookieValue(value = "refreshToken",
//            defaultValue = "No color found in cookie") String refreshToken) {
//
//        return "Sky is: " + refreshToken;
//    }

//    @PutMapping("/user/{id}")
//    public Result changPassword(@PathVariable("id") Long id, @RequestBody ChangePasswordDTO dto) {
//        userService.changePassword(id, dto);
//        return new Result(true, StatusCode.SUCCESS, "Change password success!");
//    }

}
