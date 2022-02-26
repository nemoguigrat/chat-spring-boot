package com.chat.reactchat.service;

import com.chat.reactchat.dto.room.CreateRoomRequest;
import com.chat.reactchat.model.RoomType;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.MessageRepository;
import com.chat.reactchat.repository.RoomRepository;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    public ChatRoom addRoom(String email, CreateRoomRequest request) {
        ChatRoom room = new ChatRoom();
        room.setName(request.getName());
        room.setRoomType(request.getRoomType());
        return addUsers(room, request.getUsers());
    }

    private ChatRoom addUsers(ChatRoom room, Set<Long> usersId){
        Set<User> users = userRepository.findUsersByIdIn(usersId);
        users.forEach(x -> x.addUserInRoom(room));
        userRepository.saveAll(users);
        return room;
    }

    public ChatRoom findRoomById(Long roomId) {
        return roomRepository.findChatRoomById(roomId);
    }
}
