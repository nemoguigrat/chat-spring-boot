package com.chat.reactchat.exception.room;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoomExistsException extends RuntimeException {
    public RoomExistsException(String msg) {
        super(msg);
    }
}
