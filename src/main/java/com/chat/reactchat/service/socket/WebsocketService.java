package com.chat.reactchat.service.socket;

import com.chat.reactchat.dto.message.TextMessageRequest;
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
    private final MemorySessionStoreService sessionStore;
    private final JsonConverterUtils jsonConverterUtils;


    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException {
            TextMessageRequest messageRequest = jsonConverterUtils.parseTextMessage(message, TextMessageRequest.class);
            ChatMessage savedMessage = messageService.saveMessage(session.getPrincipal().getName(),
                    messageRequest.getRoom(), messageRequest.getMessage());
            //генеригует сообщение для отправки другим пользователям
            TextMessage messageResponse = new TextMessage(jsonConverterUtils.convert(savedMessage));
             // получает пользователей из указанной комнаты
            Set<User> usersInCurrentRoom = userRepository.selectUsersFromRoom(savedMessage.getRoom());
            // пробегается по пользователям из комнаты и вытаскивает их сессии
            for (User user : usersInCurrentRoom) {
                WebSocketSession userSession = sessionStore.getSession(user);
                // если это не та же самая сессия и пользователь участник группы, то отправляется сообщение
                if (userSession != null && !session.equals(userSession)){
                    // TODO создать Dto с короткими данными пользователя, комнатой, самим сообщением + дополнительная информация о сообщении
                    userSession.sendMessage(messageResponse); // пока только пересылка отправленного
                }
            }
    }
}
