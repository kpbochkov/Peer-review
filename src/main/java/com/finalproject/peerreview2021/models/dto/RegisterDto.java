package com.finalproject.peerreview2021.models.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.IOException;

public class RegisterDto {
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols")
    private String username;


    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,500}$", message = "Password must be at least 8 symbols and" +
            " should contain capital letter, digit, and special symbol (+, -, *, &, ^,#,?,!,@,$,%,^,&,*,-)")
    private String password;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,500}$", message = "Password must be at least 8 symbols and" +
            " should contain capital letter, digit, and special symbol (+, -, *, &, ^,#,?,!,@,$,%,^,&,*,-)")
    private String passwordConfirm;

    @NotEmpty(message = "Email can't be empty")
    private String email;

    @Pattern(regexp = "([0-9]{10})", message = "Phone number should be 10 digits")
    private String phoneNumber;

    private byte[] photo;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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

    public void store(MultipartFile photo) throws IOException {
        this.photo = photo.getBytes();
    }
}
