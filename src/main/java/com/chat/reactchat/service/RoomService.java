package com.chat.reactchat.service;

import com.chat.reactchat.dto.message.TextMessageResponse;
import com.chat.reactchat.dto.room.CommunityRoomRequest;
import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.RoomType;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.MessageRepository;
import com.chat.reactchat.repository.RoomRepository;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public ChatRoom inviteUsers(Long chatId, Set<Long> usersId) {
        ChatRoom room = findRoomById(chatId);
        if (room.getRoomType() == RoomType.PERSONAL)
            throw new IllegalArgumentException(); // выкинуть ошибку

        return addUsers(room, usersId);
    }

    public List<TextMessageResponse> getRoomMessages(Long userId, Long roomId) {
        if (!userRepository.existsUserByIdAndRooms_Id(userId, roomId))
            throw new IllegalArgumentException(); // выкинуть ошибку
        List<ChatMessage> chatMessages = messageRepository.findChatMessagesByRoom_IdOrderByDateCreation(roomId);
        return chatMessages.stream().map(TextMessageResponse::new).collect(Collectors.toList());
    }


    public Set<ChatRoom> getUserChatRooms(Long userId) {
        User user = userRepository.findUserByIdOrThrow(userId);

        // стоит передлать алгоритм, слишком много транзакций
        Set<ChatRoom> chatRooms = user.getRooms();
        for (ChatRoom chatRoom : chatRooms)
            if (chatRoom.getRoomType() == RoomType.PERSONAL) {
                String personalRoomName = "";

                for (User member : chatRoom.getUsers())
                    if (!member.getId().equals(userId))
                        personalRoomName = member.getFirstName() + " " + member.getSecondName();
                chatRoom.setName(personalRoomName);
            }
        return chatRooms;
    }


    public ChatRoom createCommunityRoom(String userId, CommunityRoomRequest request) {
        ChatRoom room = new ChatRoom(request.getName(), RoomType.COMMUNITY);
        room = roomRepository.save(room);
        request.getUsers().add(Long.parseLong(userId));
        return addUsers(room, request.getUsers());
    }

    public ChatRoom createPersonalRoom(String userId, Long companionId) {
        // попробую при создании команты генерировать уникальный код,
        // токен из id создателя и пирглашенного с помощью какой то соли

        // добавить проверку, что такой комнаты не существует.
        String hashCode = userId + " " + companionId; // в дальнейшем алгоритм кодирования строки и сравнения кодов
        if (roomRepository.existsChatRoomsByName(hashCode))
            throw new IllegalArgumentException(); // заменить ошибку
        ChatRoom room = new ChatRoom(hashCode, RoomType.PERSONAL);
        room = roomRepository.save(room);
        return addUsers(room, new HashSet<>(Arrays.asList(Long.parseLong(userId), companionId)));
    }

    private ChatRoom addUsers(ChatRoom room, Set<Long> usersId){
        Set<User> users = userRepository.findUsersByIdIn(usersId);
        users.forEach(user -> user.getRooms().add(room));
        userRepository.saveAll(users);
        return room;
    }

    public ChatRoom findRoomById(Long roomId) {
        return roomRepository.findChatRoomByIdOrThrow(roomId);
    }
}
