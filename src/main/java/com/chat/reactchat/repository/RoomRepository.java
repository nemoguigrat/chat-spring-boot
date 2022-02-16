package com.chat.reactchat.repository;

import com.chat.reactchat.model.ChatRoom;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<ChatRoom, Long> {
    ChatRoom findChatRoomById(Long id);
}
