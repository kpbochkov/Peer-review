package com.finalproject.peerreview2021.models.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.IOException;

public class UserDto {

    @NotBlank
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*)(?=.*[@$!%*#?&])[A-Za-z@$!%*#?&]{8,}$")
    @Size(min = 8, max = 50, message = "Password must be at least 8 symbols and should contain " +
            "capital letter, lowercase letter, digit, and special symbol (+, -, *, &, ^, )")
    private String password;

    @NotBlank
    @Email
    private String email;

    @Positive(message = "Phone number should contain positive digits")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private int phoneNumber;

    private byte[] photo;

    public UserDto() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void store(MultipartFile photo) throws IOException {
       this.photo = photo.getBytes();
    }
}
