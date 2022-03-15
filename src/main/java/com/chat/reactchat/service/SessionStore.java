package com.chat.reactchat.service;

import com.chat.reactchat.model.User;
import org.springframework.web.socket.WebSocketSession;

import javax.security.auth.Subject;
import java.util.Map;

public interface SessionStore {
    void connect(WebSocketSession session);

    void disconnect(WebSocketSession session);

    WebSocketSession getSession(User user);
}
