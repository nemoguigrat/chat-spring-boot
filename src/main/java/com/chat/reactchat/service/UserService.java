package com.chat.reactchat.service;

import com.chat.reactchat.configuration.jwt.JwtTokenUtils;
import com.chat.reactchat.dto.file.UploadFileResponse;
import com.chat.reactchat.dto.user.UserResponse;
import com.chat.reactchat.exception.user.UserExistException;
import com.chat.reactchat.model.*;
import com.chat.reactchat.dto.auth.LoginRequest;
import com.chat.reactchat.dto.auth.LoginResponse;
import com.chat.reactchat.dto.auth.RegistrationRequest;
import com.chat.reactchat.repository.UserRepository;
import liquibase.util.file.FilenameUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    public LoginResponse signIn(LoginRequest request) {
        User user = userRepository.findUserByEmailOrThrow(request.getEmail());
        String token = jwtTokenUtils.generateJwtToken(user.getId());
        return new LoginResponse(
                user.getEmail(),
                user.getFirstName(),
                user.getSecondName(),
                user.getRole(),
                token
        );
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
        user.getRole().add(Role.ROLE_USER);
        return userRepository.save(user);
    }

    public UserResponse getUserProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User: " + id + " not found."));
        
        return new UserResponse(user);
    }

    public Set<User> getUsers(String searchName) {
        //TODO ???????????????? ???????? ?? ???????????????????? ?????????? ?? ???????? ????????????, ?????????? ???????? ???? ??????????, ???????? ???? ????????, ???????? ?? ???????????? ?????????????? @
        return userRepository.selectUsersByFullName(searchName);
    }
}
