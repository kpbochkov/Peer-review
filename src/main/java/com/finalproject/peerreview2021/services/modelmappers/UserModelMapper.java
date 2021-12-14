package com.finalproject.peerreview2021.services.modelmappers;

import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.dto.RegisterDto;
import com.finalproject.peerreview2021.models.dto.UserDto;
import com.finalproject.peerreview2021.repositories.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class UserModelMapper {

    private final UserRepository userRepository;

    @Autowired
    public UserModelMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User fromDto(UserDto userDto,MultipartFile photo) throws IOException {
        User user = new User();
        user.store(photo);
        dtoToObject(userDto, user);
        return user;
    }

    public User fromDto(UserDto userDto, int id) {
        User user = userRepository.getById(id);
        dtoToObject(userDto, user);
        return user;
    }


    private void dtoToObject(UserDto userDto, User user) {
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(Integer.parseInt(userDto.getPhoneNumber()));
    }

    private void dtoToObject(User userToUpdate, User user) {
        user.setPhoto(userToUpdate.getPhoto());
    }

    public User dtoToObject(RegisterDto registerDto) {
        User user = new User();
//        registerDto.store(photo);
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setEmail(registerDto.getEmail());
        user.setPhoneNumber(Integer.parseInt(registerDto.getPhoneNumber()));
 //       user.setPhoto(registerDto.getPhoto());

        return user;
    }
}
