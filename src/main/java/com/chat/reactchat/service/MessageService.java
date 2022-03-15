package com.chat.reactchat.service;


import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.MessageRepository;
import com.chat.reactchat.repository.RoomRepository;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MessageService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    // сохранение в отдельной транзакции, так как сообщения пользователя подгружаются лениво
    @Transactional
    public ChatMessage saveMessage(String userId, Long roomId, String text) {
        User user = userRepository.findUserByIdOrThrow(Long.parseLong(userId)); // стоит ли использовать entity manager
        ChatRoom chatRoom = roomRepository.findChatRoomByIdOrThrow(roomId);
        if (!user.getRooms().contains(chatRoom))
            throw new IllegalArgumentException(); //TODO заменить ошибку
        ChatMessage message = new ChatMessage(text, user, chatRoom);
        user.getMessages().add(message);
        userRepository.save(user);  // не понятно стоит ли сохранять родительскую сущность или достаточно только дочерней
        return message;
    }
}
