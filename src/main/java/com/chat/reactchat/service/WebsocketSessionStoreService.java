package com.chat.reactchat.service;

import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class WebsocketSessionStoreService implements SessionStore{
    private final Map<User, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final UserRepository userRepository;

    @Override
    public void connect(WebSocketSession session) {
        User user = userRepository.findUserByIdOrThrow(Long.parseLong(session.getPrincipal().getName()));
        sessions.put(user, session);
    }

    @Override
    public void disconnect(WebSocketSession session) {
        sessions.entrySet().removeIf(set -> set.getValue().equals(session));
    }

    @Override
    public WebSocketSession getSession(User user) {
        return sessions.getOrDefault(user, null);
    }
}
