package com.chat.reactchat.service;


import com.chat.reactchat.exception.room.UserRoomAccessException;
import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import com.chat.reactchat.model.UserRoomEntity;
import com.chat.reactchat.repository.MessageRepository;
import com.chat.reactchat.repository.RoomRepository;
import com.chat.reactchat.repository.UserRepository;
import com.chat.reactchat.repository.UserRoomEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRoomEntityRepository userRoomEntityRepository;

    @Transactional
    public ChatMessage saveMessage(String userId, Long roomId, String text) {
        UserRoomEntity userRoomEntity = userRoomEntityRepository.
                findUserRoomEntityUserIdAndRoomId(Long.parseLong(userId), roomId)
                .orElseThrow(() -> new UserRoomAccessException("User not a room member " + roomId + " or not exist."));
        ChatMessage message = new ChatMessage(text, userRoomEntity.getUser(), userRoomEntity.getRoom());
        return messageRepository.save(message);
    }
}
