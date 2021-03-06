package com.chat.reactchat.repository;

import com.chat.reactchat.exception.room.RoomExistsException;
import com.chat.reactchat.model.ChatRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoomRepository extends CrudRepository<ChatRoom, Long> {
    default ChatRoom findChatRoomByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new RoomExistsException("Room " + id + " not found"));
    }

    Boolean existsChatRoomsByNameOrName(String name, String alternate);

    Set<ChatRoom> findChatRoomsByUsers_Id(Long id);
}
