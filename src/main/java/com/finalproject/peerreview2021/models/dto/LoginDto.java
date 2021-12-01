package com.finalproject.peerreview2021.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class LoginDto {
    @NotEmpty
    private String username;

    @NotEmpty(message = "Password must be at least 8 symbols and should contain " +
            "capital letter, digit, and special symbol (+, -, *, &, ^, …)")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*)(?=.*[@$!%*#?&])[A-Za-z@$!%*#?&]{8,}$")
    private String password;

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
}
