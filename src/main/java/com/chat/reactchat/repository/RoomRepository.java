package com.chat.reactchat.repository;

import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RoomRepository extends CrudRepository<ChatRoom, Long> {
    ChatRoom findChatRoomById(Long id);
}
