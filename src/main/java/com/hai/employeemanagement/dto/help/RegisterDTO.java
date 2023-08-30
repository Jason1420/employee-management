package com.hai.employeemanagement.dto.help;

import com.hai.employeemanagement.entity.help.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    private String username;

    private String password;
    private String code;
    @NotBlank(message = "First name should not be blank")
    @Pattern(regexp = "^[a-zA-Z ]{1,25}$", message = "re-enter first name consisting of 1 to 25 alphanumeric characters")
    private String firstName;
    @NotBlank(message = "Last name should not be blank")
    @Pattern(regexp = "^[a-zA-Z ]{1,25}$", message = "re-enter last name consisting of 1 to 25 alphanumeric characters")
    private String lastName;
    @NotBlank(message = "email should not be blank")
    @Email(message = "Invalid email")
    private String email;
    private Gender gender;
    private String dateOfBirth;
    @Pattern(regexp = "^0[0-9]{9}$", message = "Invalid phone number")
    private String phoneNumber;
}
