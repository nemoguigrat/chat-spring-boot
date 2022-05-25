package com.chat.reactchat.service;

import com.chat.reactchat.dto.message.TextMessageResponse;
import com.chat.reactchat.dto.room.CommunityRoomRequest;
import com.chat.reactchat.dto.room.RoomDto;
import com.chat.reactchat.exception.room.RoomExistsException;
import com.chat.reactchat.exception.room.UnsupportedRoomActionException;
import com.chat.reactchat.exception.room.UserRoomAccessException;
import com.chat.reactchat.model.*;
import com.chat.reactchat.repository.MessageRepository;
import com.chat.reactchat.repository.RoomRepository;
import com.chat.reactchat.repository.UserRepository;
import com.chat.reactchat.repository.UserRoomEntityRepository;
import lombok.AllArgsConstructor;
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
    private final UserRoomEntityRepository userRoomEntityRepository;

    public ChatRoom inviteUsers(Long userId, Long roomId, Set<Long> usersId) {
        if (!userRoomEntityRepository.existsById_UserIdAndId_RoomId(userId, roomId))
            throw new UserRoomAccessException("User not a room member " + roomId + " or not exist.");
        ChatRoom room = roomRepository.findChatRoomByIdOrThrow(roomId);
        if (room.getRoomType() == RoomType.PERSONAL)
            throw new UnsupportedRoomActionException("Unsupported action for room " + roomId);

        return addUsers(room, usersId);
    }

    public List<TextMessageResponse> getRoomMessages(Long userId, Long roomId) {
        if (!userRoomEntityRepository.existsById_UserIdAndId_RoomId(userId, roomId))
            throw new UserRoomAccessException("User not a room member " + roomId + " or not exist.");
        List<ChatMessage> chatMessages = messageRepository.findChatMessagesByRoom_IdOrderByDateCreation(roomId);
        return chatMessages.stream().map(TextMessageResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public List<RoomDto> getUserChatRooms(Long userId) {
        User user = userRepository.findUserByIdOrThrow(userId);
        Map<Long, ChatRoom> companionIdPersonalRoom = new HashMap<>();
        List<RoomDto> rooms = new ArrayList<>();

        Set<ChatRoom> userChatRooms = user.getRooms();
        for (ChatRoom chatRoom : userChatRooms) {
            if (chatRoom.getRoomType() == RoomType.PERSONAL) {
                String[] usersId = chatRoom.getName().split(" ");
                String companionId = userId.toString().equals(usersId[0]) ? usersId[1] : usersId[0];
                companionIdPersonalRoom.put(Long.parseLong(companionId), chatRoom);
            } else {
                rooms.add(new RoomDto(chatRoom));
            }
        }
        Set<User> companions = userRepository.findUsersByIdIn(companionIdPersonalRoom.keySet());
        for (User companion : companions){
            ChatRoom currentRoom = companionIdPersonalRoom.get(companion.getId());
            RoomDto roomDto = new RoomDto(currentRoom);
            roomDto.setName(companion.getFirstName() + " " + companion.getSecondName());
            rooms.add(roomDto);
        }
//        rooms.sort(); отсортировать по дате последнего сообщения
        return rooms;
    }

    public ChatRoom createCommunityRoom(Long userId, CommunityRoomRequest request) {
        ChatRoom room = new ChatRoom(request.getName(), RoomType.COMMUNITY);
        request.getUsers().add(userId);
        return addUsers(room, request.getUsers());
    }

    public ChatRoom createPersonalRoom(Long userId, Long companionId) {
        String hashCode = userId + " " + companionId;
        if (roomRepository.existsChatRoomsByNameOrName(hashCode, companionId + " " + userId))
            throw new RoomExistsException();
        ChatRoom room = new ChatRoom(hashCode, RoomType.PERSONAL);
        return addUsers(room, new HashSet<>(Arrays.asList(userId, companionId)));
    }

    private ChatRoom addUsers(ChatRoom room, Set<Long> usersId) {
        Set<User> users = userRepository.findUsersByIdIn(usersId);
        if (users.size() < usersId.size())
            throw new IllegalArgumentException(); //Заменить ошибку
        Set<UserRoomEntity> userRoomEntities = new HashSet<>();
        if (room.getId() == null)
            room = roomRepository.save(room);
        for (User user : users) {
            UserRoomEntity entity = new UserRoomEntity();
            entity.setUser(user);
            entity.setRoom(room);
            userRoomEntities.add(entity);
        }
        userRoomEntityRepository.saveAll(userRoomEntities);
        return room;
    }
}