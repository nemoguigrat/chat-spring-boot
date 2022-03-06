package com.chat.reactchat.service;

import com.chat.reactchat.dto.message.TextMessageRequest;
import com.chat.reactchat.dto.message.TextMessageResponse;
import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class WebsocketService {
    private final MessageService messageService;
    private final UserRepository userRepository;
    private final Map<User, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException {
        TextMessageRequest messageRequest = objectMapper.readValue(message.getPayload(), TextMessageRequest.class);
        //TODO исправить Dto для отправки сообщений
        if (messageRequest.getRoom() != null && messageRequest.getMessage() != null) {
            ChatMessage savedMessage = messageService.saveMessage(session.getPrincipal().getName(),
                    messageRequest.getRoom(), messageRequest.getMessage());
            // получает пользователей из указанной комнаты
            Set<User> usersInCurrentRoom = userRepository.selectUsersFromRoom(savedMessage.getRoom());

            //генеригует сообщение для отправки другим пользователям
            TextMessage messageResponse = new TextMessage(new TextMessageResponse(savedMessage).toString());

            // пробегается по пользователям из комнаты и вытаскивает их сессии
            for (User user : usersInCurrentRoom) {
                WebSocketSession userSession = sessions.getOrDefault(user, null);
                // если это не та же самая сессия и пользователь участник группы, то отправляется сообщение
                if (userSession != null && !session.equals(userSession)){
                    // TODO создать Dto с короткими данными пользователя, комнатой, самим сообщением + дополнительная информация о сообщении
                    userSession.sendMessage(messageResponse); // пока только пересылка отправленного
                }
            }
        }
    }

    public void addSession(WebSocketSession session) {
        // сопоставляет пользователя и сессию, что бы не лазить в базу в лишний раз
        User user = userRepository.findUserByEmail(session.getPrincipal().getName()).get();
        sessions.put(user, session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.entrySet().removeIf(set -> set.getValue().equals(session));
    }
}