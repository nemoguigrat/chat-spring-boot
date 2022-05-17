package com.chat.reactchat.controller;

import com.chat.reactchat.dto.message.TextMessageResponse;
import com.chat.reactchat.model.ChatMessage;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.dto.room.CommunityRoomRequest;
import com.chat.reactchat.model.UserRoomEntity;
import com.chat.reactchat.repository.RoomRepository;
import com.chat.reactchat.service.RoomService;
import com.chat.reactchat.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("api/chat")
@AllArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService roomService;
    private final UserService userService;
    private final RoomRepository roomRepository;

    @PostMapping("/community-rooms")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createCommunityRoom(Principal principal, @RequestBody CommunityRoomRequest request) {
        roomService.createCommunityRoom(principal.getName(), request);
    }

    @PostMapping("/personal-rooms/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createPersonalRoom(Principal principal, @PathVariable Long userId) {
        roomService.createPersonalRoom(principal.getName(), userId);
    }

    @PostMapping("/{roomId}/members")
    public void addMembers(Principal principal, @RequestBody Set<Long> usersId, @PathVariable Long roomId) {
        roomService.inviteUsers(Long.parseLong(principal.getName()), roomId, usersId);
    }

    @GetMapping("/rooms")
    public List<ChatRoom> getUserChatRooms(Principal principal) {
        return roomService.getUserChatRooms(Long.parseLong(principal.getName()));
    }

    @GetMapping("/messages/{roomId}")
    public List<TextMessageResponse> getChatMessagesInRoom(Principal principal, @PathVariable Long roomId) {
        return roomService.getRoomMessages(Long.parseLong(principal.getName()), roomId);
    }
}
