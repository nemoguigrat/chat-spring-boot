package com.chat.reactchat.service;

import com.chat.reactchat.configuration.jwt.JwtConfiguration;
import com.chat.reactchat.configuration.jwt.JwtUtils;
import com.chat.reactchat.dto.user.UserResponse;
import com.chat.reactchat.exception.user.UserExistException;
import com.chat.reactchat.model.*;
import com.chat.reactchat.dto.auth.LoginRequest;
import com.chat.reactchat.dto.auth.LoginResponse;
import com.chat.reactchat.dto.auth.RegistrationRequest;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public LoginResponse signIn(LoginRequest request) {
        User user = userRepository.findUserByEmailOrThrow(request.getEmail());
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("username", user.getId().toString());
            String authorities = user.getRole().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            claims.put("authorities", authorities);
            String jwt = jwtUtils.createJwtForClaims(user.getId().toString(), claims);
            return new LoginResponse(
                    user.getEmail(),
                    user.getFirstName(),
                    user.getSecondName(),
                    user.getRole(),
                    jwt
            );
        } else {
            throw new UserExistException(""); // заменить ошибку
        }
    }

    @Transactional
    public void loadImage(Long userId, MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        Image image = new Image(fileName);
        User user = userRepository.findUserByIdOrThrow(userId);
        user.setImage(image);
        userRepository.save(user);
    }

    public User singUp(RegistrationRequest request) throws IllegalArgumentException {
        if (userRepository.existsUserByEmail(request.getEmail()))
            throw new UserExistException("User with email " + request.getEmail() + " already exists");
        User user = new User(request.getEmail(), request.getFirstName(), request.getSecondName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRole().add(Role.USER);
        return userRepository.save(user);
    }

    public UserResponse getUserProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User: " + id + " not found."));
        
        return new UserResponse(user);
    }

    public Set<User> getUsers(String searchName) {
        //TODO добавить поле с уникальным ником в базу данных, поиск либо по имени, либо по нику, если в начале запроса @
        return userRepository.selectUsersByFullName(searchName);
    }
}
