package com.chat.reactchat.pojo.room;

import com.chat.reactchat.enums.RoomType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class CreateRoomRequest {
    private String name;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;
}
