package com.essay.TieuLuan_BE.controller;

import com.essay.TieuLuan_BE.dto.DirectMessageDto;
import com.essay.TieuLuan_BE.dto.UserDto;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.request.MessageRequest;
import com.essay.TieuLuan_BE.service.UserService;
import com.essay.TieuLuan_BE.service.messageService.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @PostMapping("/{recipientId}")
    public ResponseEntity<DirectMessageDto> send(
            @PathVariable Long recipientId,
            @RequestBody MessageRequest body,
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User me = userService.findUserProfileByJwt(jwt);
        DirectMessageDto dto = messageService.sendMessage(
                me, recipientId, body.getContent(), body.getImageUrl()
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> listPartners(@RequestHeader("Authorization") String jwt) throws UserException {
        User me = userService.findUserProfileByJwt(jwt);
        return ResponseEntity.ok(messageService.listConversationPartners(me));
    }

    @GetMapping("/{otherId}")
    public ResponseEntity<List<DirectMessageDto>> getConversation(
            @PathVariable Long otherId,
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User me = userService.findUserProfileByJwt(jwt);
        return ResponseEntity.ok(messageService.getConversation(me.getId(), otherId));
    }
}
