package com.github.commerce.web.controller.chat;

import com.github.commerce.repository.user.UserDetailsImpl;
import com.github.commerce.service.chat.ChatService;
import com.github.commerce.web.dto.chat.ChatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/chat")
@RestController
public class ChatController {
    private final ChatService chatService;

    @CrossOrigin(origins = "*")
    @GetMapping("/user/{sellerId}")
    public ResponseEntity<Map<String, Object>> getUserChats(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("sellerId") Long sellerId

    ) {
        Long userId = userDetails.getUser().getId();
        return ResponseEntity.ok(chatService.getUserChatList(userId, sellerId));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/seller/{sellerId}/{productId}")
    public ResponseEntity<Map<String, Object>> getSellerChats(
            //@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("sellerId") Long sellerId,
            @PathVariable("productId") Long productId

    ) {
        return ResponseEntity.ok(chatService.getSellerChatList(sellerId, productId));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/detail/{customRoomId}")
    public ResponseEntity<ChatDto> getChatRoom(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable String customRoomId
    ) {

        return ResponseEntity.ok(chatService.getChatRoom(customRoomId));
    }
}
