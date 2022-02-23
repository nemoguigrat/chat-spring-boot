package com.chat.reactchat.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TextMessageDto implements Serializable {
    String message;
    Long room;
}
