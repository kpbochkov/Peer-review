package com.finalproject.peerreview2021.controllers.rest;

import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.models.dto.UserDto;
import com.finalproject.peerreview2021.services.contracts.UserService;
import com.finalproject.peerreview2021.services.modelmappers.UserModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserModelMapper userMapper;
    private final AuthenticationHelper authenticationHelper;

    public UserController(UserService userService, UserModelMapper userMapper,
                          AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping()
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        try {
            return userService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public User create(@RequestPart MultipartFile photo,
                       @Valid @RequestPart UserDto userDto) {
        try {
            User user = userMapper.fromDto(userDto, photo);
            userService.create(user);
            return user;
        } catch (DuplicateEntityException | IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public User update(@PathVariable int id, @Valid @RequestBody UserDto userDto) {
        try {
            User user = userMapper.fromDto(userDto, id);
            userService.update(user);
            return user;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            userService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/filter")
    public List<User> filter(@RequestParam(required = false) Optional<String> username,
                             Optional<String> email, Optional<Integer> phone) {
        try {
            return userService.filter(username, email, phone);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/workitems")
    public List<WorkItem> showAllWorkitemsForUser(@RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        return userService.getAllWorkitemsForUser(user);
    }


}
