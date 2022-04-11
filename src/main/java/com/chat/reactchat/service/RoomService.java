package com.chat.reactchat.service;

import com.chat.reactchat.dto.message.TextMessageResponse;
import com.chat.reactchat.dto.room.CommunityRoomRequest;
import com.chat.reactchat.exception.room.UnsupportedRoomActionException;
import com.chat.reactchat.exception.room.UserRoomAccessException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public ChatRoom inviteUsers(Long userId, Long roomId, Set<Long> usersId) {
        if (!userRepository.existsUserByIdAndRooms_Id(userId, roomId))
            throw new UserRoomAccessException("User not a room member " + roomId + " or not exist.");
        ChatRoom room = findRoomById(roomId);
        if (room.getRoomType() == RoomType.PERSONAL)
            throw new UnsupportedRoomActionException("Unsupported action for room " + roomId);

        return addUsers(room, usersId);
    }

    public List<TextMessageResponse> getRoomMessages(Long userId, Long roomId) {
        if (!userRepository.existsUserByIdAndRooms_Id(userId, roomId))
            throw new UserRoomAccessException("User not a room member " + roomId + " or not exist.");
        List<ChatMessage> chatMessages = messageRepository.findChatMessagesByRoom_IdOrderByDateCreation(roomId);
        return chatMessages.stream().map(TextMessageResponse::new).collect(Collectors.toList());
    }


    public List<ChatRoom> getUserChatRooms(Long userId) {
        User user = userRepository.findUserByIdOrThrow(userId);
        Map<Long, ChatRoom> companionIdPersonalRoom = new HashMap<>();
        List<ChatRoom> rooms = new ArrayList<>();
        // стоит передлать алгоритм, слишком много транзакций

        Set<ChatRoom> userChatRooms = user.getRooms();
        for (ChatRoom chatRoom : userChatRooms) {
            if (chatRoom.getRoomType() == RoomType.PERSONAL) {
                String[] usersId = chatRoom.getName().split(" ");
                String companionId = userId.toString().equals(usersId[0]) ? usersId[1] : usersId[0];
                companionIdPersonalRoom.put(Long.parseLong(companionId), chatRoom);
            } else {
                rooms.add(chatRoom);
            }
        }
        Set<User> companions = userRepository.findUsersByIdIn(companionIdPersonalRoom.keySet());
        for (User companion : companions){
            ChatRoom currentRoom = companionIdPersonalRoom.get(companion.getId());
            currentRoom.setName(companion.getFirstName() + " " + companion.getSecondName());
            rooms.add(currentRoom);
        }
//        rooms.sort(); отсортировать по дате последнего сообщения
        return rooms;
    }

    public ChatRoom createCommunityRoom(String userId, CommunityRoomRequest request) {
        ChatRoom room = new ChatRoom(request.getName(), RoomType.COMMUNITY);
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
        return addUsers(room, new HashSet<>(Arrays.asList(Long.parseLong(userId), companionId)));
    }

    private ChatRoom addUsers(ChatRoom room, Set<Long> usersId) {
        Set<User> users = userRepository.findUsersByIdIn(usersId);
        if (users.size() < usersId.size())
            throw new IllegalArgumentException(); //Заменить ошибку
        users.forEach(user -> user.addUserInRoom(roomRepository.save(room)));
        userRepository.saveAll(users);
        return room;
    }

    public ChatRoom findRoomById(Long roomId) {
        return roomRepository.findChatRoomByIdOrThrow(roomId);
    }
}
