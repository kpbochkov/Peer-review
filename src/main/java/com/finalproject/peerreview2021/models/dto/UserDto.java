package com.finalproject.peerreview2021.models.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.IOException;

public class UserDto {

    @NotBlank
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols")
    private String username;

    @NotEmpty(message = "Password confirmation can't be empty")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    @Size(min = 8, max = 500, message = "Password must be at least 8 symbols and should contain " +
            "capital letter, digit, and special symbol (+, -, *, &, ^, …)")
    private String password;


    @NotEmpty(message = "Password confirmation can't be empty")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    private String passwordConfirm;

    @NotEmpty(message = "Email can't be empty")
    private String email;

    @Size(min = 10, max = 10, message = "Phone number should be 10 digits")
    @NotNull
    @Pattern(regexp = "([0-9]{10})")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
