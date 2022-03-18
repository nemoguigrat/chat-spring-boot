package com.chat.reactchat.repository;

import com.chat.reactchat.model.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<ChatMessage, Long> {
    List<ChatMessage> findChatMessagesByRoom_IdOrderByDateCreation(Long roomId);
}
