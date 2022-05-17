package com.chat.reactchat.dto.message;

import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TextMessageResponse implements Serializable {
    private Long id;
    private Long senderId;
    private String message;
    private Long room;
    private String firstName;
    private String secondName;
    private String dateCreation;

    public TextMessageResponse(ChatMessage message) {
        this.id = message.getId();
        User sender = message.getUser();
        this.senderId = sender.getId();
        this.message = message.getMessage();
        this.room = message.getRoom().getId();
        this.firstName = sender.getFirstName();
        this.secondName = sender.getSecondName();
        this.dateCreation = message.getDateCreation().toString();
    }
}
