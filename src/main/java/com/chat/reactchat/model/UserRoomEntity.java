package com.chat.reactchat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users_rooms")
@Data
@NoArgsConstructor
public class UserRoomEntity {
    @EmbeddedId
    private UserRoomsKey id = new UserRoomsKey();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    private ChatRoom room;

    public UserRoomEntity(UserRoomsKey key) {
        this.id = key;
    }
}
