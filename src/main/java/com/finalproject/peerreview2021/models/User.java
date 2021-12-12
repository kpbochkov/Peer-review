package com.finalproject.peerreview2021.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;

@Table(name = "users", indexes = {
        @Index(name = "users_phone_number_uindex", columnList = "phone_number", unique = true),
        @Index(name = "users_email_uindex", columnList = "email", unique = true)
})
@Entity
public class User implements SoftDeletable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols")
    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private Integer phoneNumber;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_teams",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )

    private Set<Team> teams;

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

//    public String getStringPhoto(byte[] photo) {
//        String newString = Base64.getEncoder().encodeToString(photo);
//        return newString;
//    }

    public String getImage() {
        String image;
        if (this.getPhoto() == null) {
            image = null;
        } else {
            image = "data:image/png/pdf/doc;base64," + Base64.getEncoder().encodeToString(this.getPhoto());
        }
        return image;
    }

    public void store(MultipartFile photo) throws IOException {
        this.photo = photo.getBytes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id); /*&& Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber);*/
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, password, email, phoneNumber);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}