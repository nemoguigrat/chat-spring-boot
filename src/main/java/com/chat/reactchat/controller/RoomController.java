package com.chat.reactchat.controller;

import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import com.chat.reactchat.dto.room.CreateRoomRequest;
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

    @PostMapping("/rooms")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createRoom(Principal principal, @RequestBody CreateRoomRequest request){
        roomService.addRoom(principal.getName(), request);
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
