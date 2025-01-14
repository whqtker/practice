package com.whqtker.polling_sse_ws.domain.websocket.controller;

import com.whqtker.polling_sse_ws.domain.websocket.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebsocketController {
    private final WebsocketService websocketService;

    @GetMapping("/websocket")
    public String websocket() {
        websocketService.startWebsocket();
        return "websocket";
    }

    @MessageMapping("/start")
    @SendTo("/topic/messages")
    public String handleWebSocketMessage(String message) {
        return "새로운 데이터가 도착했습니다!";
    }
}
