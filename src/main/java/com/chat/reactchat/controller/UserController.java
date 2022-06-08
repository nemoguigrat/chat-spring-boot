package com.chat.reactchat.controller;

import com.chat.reactchat.dto.file.UploadFileResponse;
import com.chat.reactchat.dto.user.UserResponse;
import com.chat.reactchat.model.CustomUserDetails;
import com.chat.reactchat.model.User;
import com.chat.reactchat.dto.auth.LoginRequest;
import com.chat.reactchat.dto.auth.LoginResponse;
import com.chat.reactchat.dto.auth.RegistrationRequest;
import com.chat.reactchat.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("user/upload")
    public void uploadFile(JwtAuthenticationToken principal,
                                         @RequestParam("file") MultipartFile file) {
        userService.loadImage(Long.parseLong(principal.getName()), file);
    }

    @GetMapping("/user")
    public UserResponse getCurrentUser(JwtAuthenticationToken principal) {
        log.error(principal.getAuthorities().toString());
        return userService.getUserProfile(Long.parseLong(principal.getName()));
    }

    @GetMapping("/users")
    public Set<User> getUsers(@RequestParam String searchName) {
        return userService.getUsers(searchName);
    }
}