package com.chat.reactchat.exception.room;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
public class UnsupportedRoomActionException extends RuntimeException {
    public UnsupportedRoomActionException(String msg) {
        super(msg);
    }
}
