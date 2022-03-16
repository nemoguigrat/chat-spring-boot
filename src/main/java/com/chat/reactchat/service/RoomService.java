package com.chat.reactchat.service;

import com.chat.reactchat.dto.room.CommunityRoomRequest;
import com.chat.reactchat.model.RoomType;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import com.chat.reactchat.repository.MessageRepository;
import com.chat.reactchat.repository.RoomRepository;
import com.chat.reactchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
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

    public ChatRoom createCommunityRoom(String userId, CommunityRoomRequest request) {
        ChatRoom room = new ChatRoom();
        room.setRoomType(RoomType.COMMUNITY);
        room.setName(request.getName());
        room = roomRepository.save(room);
        request.getUsers().add(Long.parseLong(userId));
        return addUsers(room, request.getUsers());
    }

    public ChatRoom createPersonalRoom(String userId, Long companionId) {
        // добавить проверку, что такой комнаты не существует.
        ChatRoom room = new ChatRoom();
        room.setRoomType(RoomType.PERSONAL);
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
