package com.chat.reactchat.dto.room;

import com.chat.reactchat.model.RoomType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Data
public class CreateRoomRequest {
    private String name;
    private Set<Long> users;
    private RoomType roomType;
}
