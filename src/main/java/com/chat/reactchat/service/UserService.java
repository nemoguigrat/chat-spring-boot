package com.chat.reactchat.service;

import com.chat.reactchat.configuration.jwt.JwtTokenUtils;
import com.chat.reactchat.enums.Role;
import com.chat.reactchat.model.User;
import com.chat.reactchat.pojo.auth.LoginRequest;
import com.chat.reactchat.pojo.auth.LoginResponse;
import com.chat.reactchat.pojo.auth.RegistrationRequest;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    public LoginResponse signIn(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User user = findByEmail(principal.getUsername());
        String token = jwtTokenUtils.generateJwtToken(user.getEmail());
        return new LoginResponse(
                user.getEmail(),
                user.getFirstName(),
                user.getSecondName(),
                user.getRole(),
                token
        );
    }

    public User singUp(RegistrationRequest request) throws IllegalArgumentException{
        if (!userRepository.existsUserByEmail(request.getEmail())){
            User user = new User(request.getEmail(), request.getFirstName(), request.getSecondName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.getRole().add(Role.ROLE_USER);
            return userRepository.save(user);
        }
        else
            throw new IllegalArgumentException("this user exists");
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь: " + email + " не найден."));
    }

    public Set<User> findUserInCollection(Set<Long> usersId){
        return userRepository.findUsersByIdIn(usersId);
    }
}
