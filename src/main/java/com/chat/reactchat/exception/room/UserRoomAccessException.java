package com.chat.reactchat.exception.room;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
public class UserRoomAccessException extends RuntimeException{
    public UserRoomAccessException(String msg) {
        super(msg);
    }
}
