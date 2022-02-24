package com.chat.reactchat.service;

import com.chat.reactchat.dto.TextMessageDto;
import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.MessageRepository;
import com.chat.reactchat.repository.RoomRepository;
import com.chat.reactchat.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@AllArgsConstructor
public class WebsocketService {
    private final ChatService chatService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException {
        TextMessageDto messageData = objectMapper.readValue(message.getPayload(), TextMessageDto.class);

        if (messageData.getRoom() != null && messageData.getMessage() != null) {
            ChatMessage savedMessage = chatService.saveMessage(session.getPrincipal().getName(),
                    messageData.getRoom(), messageData.getMessage());
            ChatRoom currentRoom = savedMessage.getRoom();
            Set<User> usersInCurrentRoom = userRepository.selectUsersFromRoom(currentRoom);
            for (WebSocketSession webSocketSession : sessions) {
                if (!session.equals(webSocketSession)){
                    if (usersInCurrentRoom.contains(savedMessage.getSender()))
                        webSocketSession.sendMessage(message);
                } // отправить тем пользователям, которые содержаться в комнате currentRoom
            }
        }
    }

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }


}
