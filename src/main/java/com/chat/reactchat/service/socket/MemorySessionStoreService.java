package com.chat.reactchat.service.socket;

import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class MemorySessionStoreService implements SessionStore{
    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void connect(WebSocketSession session) {
        Long userId = Long.parseLong(session.getPrincipal().getName());
        sessions.put(userId, session);
    }

    @Override
    public void disconnect(WebSocketSession session) {
        sessions.entrySet().removeIf(set -> set.getValue().equals(session));
    }

    @Override
    public WebSocketSession getSession(Long userId) {
        return sessions.getOrDefault(userId, null);
    }
}
