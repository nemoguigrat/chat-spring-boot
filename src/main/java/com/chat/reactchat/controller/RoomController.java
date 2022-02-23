package com.chat.reactchat.controller;

import com.chat.reactchat.model.RoomType;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import com.chat.reactchat.dto.room.CreateRoomRequest;
import com.chat.reactchat.service.RoomService;
import com.chat.reactchat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;


@RestController
@RequestMapping("api/chat")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final UserService userService;

    @SubscribeMapping
    @PostMapping("/create")
    public ResponseEntity<?> createCommunityChat(Principal principal, @RequestBody CreateRoomRequest request){
        User user = userService.findByEmail(principal.getName());
        if (request.getRoomType() == RoomType.COMMUNITY)
            roomService.addCommunityRoom(user, request.getName());
        else if (request.getRoomType() == RoomType.PERSONAL)
            roomService.addPersonalRoom(user, request.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{roomId}/add-members")
    public ResponseEntity<?> addMembers(@RequestBody Set<Long> usersId, @PathVariable Long roomId){
        Set<User> users = userService.findUserInCollection(usersId);
        roomService.addUsersToRoom(roomId, users);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/rooms")
    public ResponseEntity<Set<ChatRoom>> getUserChatRooms(Principal principal){
        User user = userService.findByEmail(principal.getName());
        return ResponseEntity.ok(user.getRooms());
    }
}
