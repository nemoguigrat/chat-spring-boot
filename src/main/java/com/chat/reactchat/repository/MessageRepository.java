package com.chat.reactchat.repository;

import com.chat.reactchat.model.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<ChatMessage, Long> {
    // настроить пагинацию для сообщений
    List<ChatMessage> findChatMessagesByRoom_IdOrderByDateCreation(Long roomId);
}
