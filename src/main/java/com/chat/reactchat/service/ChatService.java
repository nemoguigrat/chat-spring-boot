package com.chat.reactchat.service;


import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.RoomRepository;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class ChatService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public void saveMessage(String email, Long roomId, String text){
        User user = userRepository.findUserByEmail(email).get();
        ChatRoom chatRoom = roomRepository.findById(roomId).get();
        ChatMessage message = new ChatMessage(text, user, chatRoom);
        user.getMessages().add(message);
        userRepository.save(user);
    }
}
