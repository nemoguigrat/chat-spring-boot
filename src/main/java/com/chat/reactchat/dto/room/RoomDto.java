package com.chat.reactchat.dto.room;

import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.Image;
import com.chat.reactchat.model.RoomType;
import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String name;
    private RoomType roomType;
    private Image image;

    public RoomDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.name = chatRoom.getName();
        this.roomType = chatRoom.getRoomType();
        this.image = chatRoom.getImage();
    }
}
