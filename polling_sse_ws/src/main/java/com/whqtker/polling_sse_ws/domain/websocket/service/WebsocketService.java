package com.whqtker.polling_sse_ws.domain.websocket.service;

import com.whqtker.polling_sse_ws.domain.websocket.dto.WebSocketMessageDto;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebsocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();
    private Thread websocketThread;
    private volatile boolean isRunning = false;

    public synchronized void startWebsocket() {
        if (isRunning) {
            return;
        }

        isRunning = true;
        websocketThread = new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(500 + random.nextInt(1000));
                    log.debug("WebSocket 데이터 전송 중...");
                    messagingTemplate.convertAndSend("/topic/messages",
                            new WebSocketMessageDto("새로운 데이터가 도착했습니다!"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        websocketThread.start();
        log.info("WebSocket Producer 시작됨");
    }

    public synchronized void stopWebsocket() {
        isRunning = false;
        if (websocketThread != null) {
            websocketThread.interrupt();
        }
        log.info("WebSocket Producer 중지됨");
    }
}

