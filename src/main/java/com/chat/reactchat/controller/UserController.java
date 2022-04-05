package com.chat.reactchat.controller;

import com.chat.reactchat.model.User;
import com.chat.reactchat.dto.auth.LoginRequest;
import com.chat.reactchat.dto.auth.LoginResponse;
import com.chat.reactchat.dto.auth.RegistrationRequest;
import com.chat.reactchat.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/login")
    public LoginResponse signInUser(@RequestBody LoginRequest request) {
        return userService.signIn(request);
    }

    @PostMapping("/auth/registration")
    public String signUpUser(@RequestBody RegistrationRequest request) {
        User user = userService.singUp(request);
        return "User with email " + user.getEmail() + " successfully registered";
    }

    @GetMapping("/user")
    public User getCurrentUser(Principal principal) {
        return userService.findById(Long.parseLong(principal.getName()));
    }

    @GetMapping("/users")
    public Set<User> getUsers(@RequestParam String searchName) {
        return userService.getUsers(searchName);
    }
}