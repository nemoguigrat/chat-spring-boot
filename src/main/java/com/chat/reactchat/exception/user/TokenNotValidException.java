package com.chat.reactchat.exception.user;

public class TokenNotValidException extends RuntimeException{
    public TokenNotValidException() {
        super();
    }

    public TokenNotValidException(String message) {
        super(message);
    }
}
