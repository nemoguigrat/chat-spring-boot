package com.chat.reactchat.dto.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.NotFound;

import java.io.Serializable;

@Data
public class TextMessageRequest implements Serializable {
    private String message;
    private Long room;

    @JsonCreator
    public TextMessageRequest(@JsonProperty(required = true) String message, @JsonProperty(required = true) Long room) {
        this.message = message;
        this.room = room;
    }
}
