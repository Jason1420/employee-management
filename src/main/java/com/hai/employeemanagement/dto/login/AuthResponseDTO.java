package com.hai.employeemanagement.dto.login;

import com.hai.employeemanagement.dto.EmployeeDTO;
import com.hai.employeemanagement.dto.UserDTO;
import com.hai.employeemanagement.entity.help.Gender;
import lombok.Data;

import java.sql.Date;

@Data
public class AuthResponseDTO  {
    private final String accessToken;
    private final String tokenType = "Bearer ";
    private UserDTO userDTO;

    public AuthResponseDTO(String accessToken, UserDTO userDTO) {
        this.accessToken = accessToken;
        this.userDTO = userDTO;
    }



    @Override
    public String toString() {
        return "AccessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'';
    }
}
