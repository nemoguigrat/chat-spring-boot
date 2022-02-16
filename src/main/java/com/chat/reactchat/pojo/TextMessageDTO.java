package com.chat.reactchat.pojo;

import lombok.Data;

@Data
public class TextMessageDTO {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
