package com.chat.reactchat.dto.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
    private String email;
    private String password;
}
