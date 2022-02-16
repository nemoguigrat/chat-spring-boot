package com.chat.reactchat.pojo.auth;

import com.chat.reactchat.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class LoginResponse implements Serializable {
    private String email;
    private String firstName;
    private String secondName;
    private Set<Role> roles;
    private String token;
}
