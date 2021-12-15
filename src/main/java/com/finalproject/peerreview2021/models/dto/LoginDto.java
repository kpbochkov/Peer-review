package com.finalproject.peerreview2021.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginDto {
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols!")
    private String username;

    @NotBlank(message = "Password field must not be blank!")
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
