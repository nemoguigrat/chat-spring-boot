package com.chat.reactchat.service;

import com.chat.reactchat.configuration.jwt.JwtTokenUtils;
import com.chat.reactchat.exception.user.UserExistException;
import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.Role;
import com.chat.reactchat.model.User;
import com.chat.reactchat.dto.auth.LoginRequest;
import com.chat.reactchat.dto.auth.LoginResponse;
import com.chat.reactchat.dto.auth.RegistrationRequest;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    public LoginResponse signIn(LoginRequest request){
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

    public User singUp(RegistrationRequest request) throws IllegalArgumentException{
        if (userRepository.existsUserByEmail(request.getEmail()))
            throw new UserExistException("User with email " + request.getEmail() + " already exists");
        User user = new User(request.getEmail(), request.getFirstName(), request.getSecondName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRole().add(Role.ROLE_USER);
        return userRepository.save(user);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User: " + id + " not found."));
    }

    public Set<User> getUsers(String searchName) {
        //TODO добавить поле с уникальным ником в базу данных, поиск либо по имени, либо по нику, если в начале запроса @
        return userRepository.selectUsersByFullName(searchName);
    }
}
