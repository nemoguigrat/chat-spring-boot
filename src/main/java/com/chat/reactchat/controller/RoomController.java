package com.chat.reactchat.controller;

import com.chat.reactchat.dto.file.UploadFileResponse;
import com.chat.reactchat.dto.message.TextMessageResponse;
import com.chat.reactchat.dto.room.CommunityRoomRequest;
import com.chat.reactchat.dto.room.RoomDto;
import com.chat.reactchat.model.CustomUserDetails;
import com.chat.reactchat.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("api/chat")
@AllArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/community-rooms")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createCommunityRoom(JwtAuthenticationToken principal,
                                    @RequestBody CommunityRoomRequest request) {
        roomService.createCommunityRoom(Long.parseLong(principal.getName()), request);
    }

    @PostMapping("/personal-rooms/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createPersonalRoom(JwtAuthenticationToken principal,
                                   @PathVariable Long userId) {
        roomService.createPersonalRoom(Long.parseLong(principal.getName()), userId);
    }

    @PostMapping("/{roomId}/members")
    public void addMembers(JwtAuthenticationToken principal,
                           @RequestBody Set<Long> usersId, @PathVariable Long roomId) {
        roomService.inviteUsers(Long.parseLong(principal.getName()), roomId, usersId);
    }

    @GetMapping("/rooms")
    public List<RoomDto> getUserChatRooms(JwtAuthenticationToken principal) {
        log.error(principal.getName());
        return roomService.getUserChatRooms(Long.parseLong(principal.getName()));
    }

    @GetMapping("/messages/{roomId}")
    public List<TextMessageResponse> getChatMessagesInRoom(JwtAuthenticationToken principal,
                                                           @PathVariable Long roomId) {
        log.error(principal.getName());
        return roomService.getRoomMessages(Long.parseLong(principal.getName()), roomId);
    }

    @PostMapping("room/{roomId}/upload")
    public void uploadFile(JwtAuthenticationToken principal,
                                         @RequestParam("file") MultipartFile file,
                                         @PathVariable Long roomId) {
        roomService.loadImage(Long.parseLong(principal.getName()), roomId, file);
    }
}
