package com.chat.reactchat.repository;

import com.chat.reactchat.exception.room.RoomNotFoundException;
import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.RoomType;
import com.chat.reactchat.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RoomRepository extends CrudRepository<ChatRoom, Long> {
    default ChatRoom findChatRoomByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new RoomNotFoundException("Room " + id + " not found"));
    }

    Boolean existsChatRoomsByNameOrName(String name, String alternate);
}
