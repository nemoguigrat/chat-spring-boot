package com.chat.reactchat.service;

import com.chat.reactchat.enums.RoomType;
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

    public ChatRoom addPersonalRoom(User user, String name){
        return new ChatRoom();
    }

    public ChatRoom addUsersToRoom(Long chatId, Set<User> users){
        ChatRoom room = findRoomById(chatId);
        users.forEach(x -> x.addUserInRoom(room));
        userRepository.saveAll(users);
        return room;
    }

    public ChatRoom addCommunityRoom(User user, String name){
        ChatRoom room = addRoom(name);
        room.setRoomType(RoomType.COMMUNITY);
        user.addUserInRoom(room);
        userRepository.save(user);
        return room;
    }

    public ChatRoom addRoom(String name){
        ChatRoom room = new ChatRoom();
        room.setName(name);
        return room;
    }

    public ChatRoom findRoomById(Long roomId){
        return roomRepository.findChatRoomById(roomId);
    }
}
