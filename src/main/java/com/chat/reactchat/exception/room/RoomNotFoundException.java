package com.chat.reactchat.exception.room;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String msg) {
        super(msg);
    }
}
