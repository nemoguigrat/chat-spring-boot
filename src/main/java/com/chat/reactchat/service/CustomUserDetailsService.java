package com.chat.reactchat.service;

import com.chat.reactchat.model.CustomUserDetails;
import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findUserByIdOrThrow(Long.parseLong(id));
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
    }
}
