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
    public void createCommunityRoom(@AuthenticationPrincipal CustomUserDetails principal,
                                    @RequestBody CommunityRoomRequest request) {
        roomService.createCommunityRoom(principal.getId(), request);
    }

    @PostMapping("/personal-rooms/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createPersonalRoom(@AuthenticationPrincipal CustomUserDetails principal,
                                   @PathVariable Long userId) {
        roomService.createPersonalRoom(principal.getId(), userId);
    }

    @PostMapping("/{roomId}/members")
    public void addMembers(@AuthenticationPrincipal CustomUserDetails principal,
                           @RequestBody Set<Long> usersId, @PathVariable Long roomId) {
        roomService.inviteUsers(principal.getId(), roomId, usersId);
    }

    @GetMapping("/rooms")
    public List<RoomDto> getUserChatRooms(@AuthenticationPrincipal CustomUserDetails principal) {
        return roomService.getUserChatRooms(principal.getId());
    }

    @GetMapping("/messages/{roomId}")
    public List<TextMessageResponse> getChatMessagesInRoom(@AuthenticationPrincipal CustomUserDetails principal,
                                                           @PathVariable Long roomId) {
        return roomService.getRoomMessages(principal.getId(), roomId);
    }

    @PostMapping("room/{roomId}/upload")
    public void uploadFile(@AuthenticationPrincipal CustomUserDetails principal,
                                         @RequestParam("file") MultipartFile file,
                                         @PathVariable Long roomId) {
        roomService.loadImage(principal.getId(), roomId, file);
    }
}
