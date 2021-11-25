package com.finalproject.peerreview2021.services.modelmappers;

import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.dto.UserDto;
import com.finalproject.peerreview2021.repositories.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapper {

    private final UserRepository userRepository;

    @Autowired
    public UserModelMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User fromDto(UserDto userDto) {
        User user = new User();
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
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPhoto(userDto.getPhoto());
    }
}
