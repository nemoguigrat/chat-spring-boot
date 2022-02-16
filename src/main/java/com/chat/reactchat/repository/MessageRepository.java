package com.chat.reactchat.repository;

import com.chat.reactchat.model.ChatMessage;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<ChatMessage, Long> {

}
