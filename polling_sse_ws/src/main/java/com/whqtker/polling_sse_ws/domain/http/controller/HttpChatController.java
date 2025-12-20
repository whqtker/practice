package com.whqtker.polling_sse_ws.domain.http.controller;

import com.whqtker.polling_sse_ws.domain.http.dto.ChatMessageDto;
import com.whqtker.polling_sse_ws.domain.http.service.HttpChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/http")
@RequiredArgsConstructor
public class HttpChatController {

    private final HttpChatService httpChatService;

    @PostMapping("/messages")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatMessageDto message) {
        httpChatService.addMessage(message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessageDto>> getMessages() {
        return ResponseEntity.ok(httpChatService.getMessages());
    }
}
