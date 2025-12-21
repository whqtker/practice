package com.whqtker.polling_sse_ws.domain.websocket.controller;

import com.whqtker.polling_sse_ws.domain.websocket.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebsocketController {
    private final WebsocketService websocketService;

    @GetMapping("/ws/producer/start")
    public String start() {
        websocketService.startWebsocket();
        return "Websocket Started";
    }

    @PostMapping("/ws/producer/stop")
    public ResponseEntity<Void> stop() {
        websocketService.stopWebsocket();
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/message")
    public void handleWebSocketMessage(String message) {
        log.info("클라이언트로부터 받은 메시지: {}", message);
    }
}

