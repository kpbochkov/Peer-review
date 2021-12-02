package com.finalproject.peerreview2021.controllers.mvc;

import com.finalproject.peerreview2021.controllers.AuthenticationHelper;
import com.finalproject.peerreview2021.exceptions.AuthenticationFailureException;
import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.dto.LoginDto;
import com.finalproject.peerreview2021.models.dto.RegisterDto;
import com.finalproject.peerreview2021.services.contracts.UserService;
import com.finalproject.peerreview2021.services.modelmappers.UserModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {


    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final UserModelMapper userModelMapper;

    @Autowired
    public AuthenticationController(UserService userService,
                                    AuthenticationHelper authenticationHelper,
                                    UserModelMapper userModelMapper) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.userModelMapper = userModelMapper;
    }



    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("register", new RegisterDto());
        return "register";
    }


    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("register") RegisterDto register,
                                 BindingResult bindingResult,
                                 @RequestParam("photo") MultipartFile photo,
                                 HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!register.getPassword().equals(register.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "password_error", "Password confirmation should match password.");
            return "register";
        }

        try {
            User user = userModelMapper.dtoToObject(register, photo);
            userService.create(user);
            return "redirect:/auth/login";
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("username", "username_error", e.getMessage());
            return "register";
        } catch (IOException e) {
            bindingResult.rejectValue("photo", "photo_error", e.getMessage());
            return "register";
        }
    }
}
