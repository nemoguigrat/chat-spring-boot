package com.chat.reactchat.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserExistException extends AuthenticationException {
    public UserExistException(String msg) {
        super(msg);
    }

    public UserExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
