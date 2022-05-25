package com.chat.reactchat.service.socket;

import com.chat.reactchat.dto.message.TextMessageRequest;
import com.chat.reactchat.dto.message.TextMessageResponse;
import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.UserRepository;
import com.chat.reactchat.service.MessageService;
import com.chat.reactchat.service.util.JsonConverterUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class WebsocketService {
    private final MessageService messageService;
    private final UserRepository userRepository;
    private final SessionStore sessionStore;
    private final JsonConverterUtils jsonConverterUtils;


    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException {
        TextMessageRequest messageRequest = jsonConverterUtils.parseTextMessage(message, TextMessageRequest.class);
        ChatMessage savedMessage = messageService.saveMessage(session.getPrincipal().getName(),
                messageRequest.getRoom(), messageRequest.getMessage());
        TextMessage messageResponse = new TextMessage(jsonConverterUtils.convert(new TextMessageResponse(savedMessage)));

         // получает пользователей из указанной комнаты
        Set<Long> usersInCurrentRoom = userRepository.selectUsersIdFromRoom(messageRequest.getRoom());
        // пробегается по пользователям из комнаты и вытаскивает их сессии
        for (Long userId : usersInCurrentRoom) {
            WebSocketSession userSession = sessionStore.getSession(userId);
            // если это не та же самая сессия и пользователь участник группы, то отправляется сообщение
            if (userSession != null){
                userSession.sendMessage(messageResponse); // пока только пересылка отправленного
            }
        }
    }
}
