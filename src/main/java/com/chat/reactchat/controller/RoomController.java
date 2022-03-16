package com.chat.reactchat.controller;

import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.dto.room.CommunityRoomRequest;
import com.chat.reactchat.service.RoomService;
import com.chat.reactchat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;


@RestController
@RequestMapping("api/chat")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final UserService userService;

    @PostMapping("/community-rooms")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createCommunityRoom(Principal principal, @RequestBody CommunityRoomRequest request){
        roomService.createCommunityRoom(principal.getName(), request);
    }

    @PostMapping("/personal-rooms/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createPersonalRoom(Principal principal, @PathVariable Long userId){
        roomService.createPersonalRoom(principal.getName(), userId);
    }

    @PostMapping("/{roomId}/members")
    public void addMembers(@RequestBody Set<Long> usersId, @PathVariable Long roomId){
        roomService.inviteUsers(roomId, usersId);
    }

    @GetMapping("/rooms")
    public Set<ChatRoom> getUserChatRooms(Principal principal){
        return userService.findById(Long.parseLong(principal.getName())).getRooms();
    }
}
