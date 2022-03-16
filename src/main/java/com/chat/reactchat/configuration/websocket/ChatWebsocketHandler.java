package com.chat.reactchat.configuration.websocket;

import com.chat.reactchat.service.socket.WebsocketService;
import com.chat.reactchat.service.socket.MemorySessionStoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
@AllArgsConstructor
public class ChatWebsocketHandler extends TextWebSocketHandler {
    private final WebsocketService websocketService;
    private final MemorySessionStoreService sessionStore;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        websocketService.sendMessage(session, message);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionStore.connect(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionStore.disconnect(session);
    }
}
