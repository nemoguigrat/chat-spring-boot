package com.chat.reactchat.service;


import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.MessageRepository;
import com.chat.reactchat.repository.RoomRepository;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class MessageService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    // сохранение в отдельной транзакции, так как сообщения пользователя подгружаются лениво
    @Transactional
    public ChatMessage saveMessage(String email, Long roomId, String text) {
        // валидировать участик комнаты этот пользователь или нет
        User user = userRepository.findUserByEmail(email).get();
        ChatRoom chatRoom = roomRepository.findById(roomId).get();
        ChatMessage message = new ChatMessage(text, user, chatRoom);
        user.getMessages().add(message);
//        userRepository.save(user);  // не понятно стоит ли сохранять родительскую сущность или достаточно только дочерней
        return messageRepository.save(message);
    }
}
