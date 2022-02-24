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
    private final UserRepository userRepository;
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException {
        TextMessageDto messageData = objectMapper.readValue(message.getPayload(), TextMessageDto.class);
        //TODO исправить Dto для отправки сообщений
        if (messageData.getRoom() != null && messageData.getMessage() != null) {
            ChatMessage savedMessage = chatService.saveMessage(session.getPrincipal().getName(),
                    messageData.getRoom(), messageData.getMessage());

            // получает пользователей из указанной комнаты
            Set<User> usersInCurrentRoom = userRepository.selectUsersFromRoom(savedMessage.getRoom());

            // пробегается по всем сессиям(? пока не придумал как сделать эффективнее)
            for (WebSocketSession webSocketSession : sessions) {

                // если это не та же самая сессия и пользователь участник группы, то отправляется сообщение
                if (!session.equals(webSocketSession) && usersInCurrentRoom.contains(savedMessage.getSender())){
                // TODO создать Dto с короткими данными пользователя, комнатой, самим сообщением + дополнительная информация о сообщении
                    webSocketSession.sendMessage(message); // пока только пересылка отправленного
                }
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
