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

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> signInUser(@RequestBody LoginRequest request) {
        log.info("Try to signin user " + request.getEmail());
        LoginResponse response = userService.signIn(request);
        log.info("User log IN");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    public String signUpUser(@RequestBody RegistrationRequest request) {
        User user = userService.singUp(request);
        return "User with email " + user.getEmail() + " successfully registered";
    }

    @GetMapping("/user")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(userService.findByEmail(principal.getName()));
    }
}