package com.chat.reactchat.pojo.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegistrationRequest implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String secondName;
}