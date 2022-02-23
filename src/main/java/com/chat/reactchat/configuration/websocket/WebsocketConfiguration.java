package com.chat.reactchat.configuration.websocket;

import com.chat.reactchat.service.WebsocketService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebsocketConfiguration implements WebSocketConfigurer {
    private final WebsocketService websocketService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getChatWebSocketHandler(), "ws/chat")
                .setAllowedOrigins("*");
    }

    @Bean
    public ChatWebsocketHandler getChatWebSocketHandler(){
        return new ChatWebsocketHandler(websocketService);
    }

}
