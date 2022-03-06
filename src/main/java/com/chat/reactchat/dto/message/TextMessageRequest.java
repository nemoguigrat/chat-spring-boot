package com.chat.reactchat.dto.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class TextMessageRequest implements Serializable {
    private String message;
    private Long room;
}
