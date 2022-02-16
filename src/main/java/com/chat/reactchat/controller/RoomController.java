package com.chat.reactchat.controller;

import com.chat.reactchat.enums.RoomType;
import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import com.chat.reactchat.pojo.room.CreateRoomRequest;
import com.chat.reactchat.service.RoomService;
import com.chat.reactchat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping
//@RequestMapping("api/chat")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final UserService userService;

    @PostMapping("/create")
    public void createCommunityChat(Principal principal, @RequestBody CreateRoomRequest request){
        User user = userService.findByEmail(principal.getName());
        if (request.getRoomType() == RoomType.COMMUNITY)
            roomService.addCommunityRoom(user, request.getName());
        else if (request.getRoomType() == RoomType.PERSONAL)
            roomService.addPersonalRoom(user, request.getName());
    }

    @PostMapping("/{roomId}/add-members")
    public void addMembers(@RequestBody Set<Long> usersId, @PathVariable Long roomId){
        Set<User> users = userService.findUserInCollection(usersId);
        users.forEach(x -> System.out.println(x.toString()));
        roomService.addUsersToRoom(roomId, users);
    }

    @GetMapping("/rooms")
    public Set<ChatRoom> getUserChatRooms(Principal principal){
        User user = userService.findByEmail(principal.getName());
        return user.getRooms();
    }
}
