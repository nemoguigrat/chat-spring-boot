package com.chat.reactchat.configuration.websocket;

import com.chat.reactchat.service.WebsocketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// TODO перенести основную логику в сервисы
@Component
@AllArgsConstructor
public class ChatWebsocketHandler extends TextWebSocketHandler {
    private final WebsocketService websocketService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        websocketService.sendMessage(session, message);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        websocketService.addSession(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        websocketService.removeSession(session);
    }
}
