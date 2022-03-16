package com.chat.reactchat.dto.room;

import com.chat.reactchat.model.RoomType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Data
public class CommunityRoomRequest {
    private String name;
    private Set<Long> users;
    // возможно потом добавлю модификаторы доступа к комнате (приватный, общий, по ссылке)
}
