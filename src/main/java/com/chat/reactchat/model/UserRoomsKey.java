package com.chat.reactchat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Embeddable
@Data
@Table(name = "users_rooms")
@NoArgsConstructor
public class UserRoomsKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "room_id")
    private Long roomId;

    public UserRoomsKey(Long userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }
}
