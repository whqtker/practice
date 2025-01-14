package com.whqtker.polling_sse_ws.domain.websocket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WebsocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();
    private Thread websocketThread;
    private volatile boolean isRunning = false;

    // 웹소켓 시작 메서드
    public synchronized void startWebsocket() {
        if (isRunning) return;
        
        isRunning = true;
        websocketThread = new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(500 + random.nextInt(1000)); // 0.5 ~ 1.5초 랜덤 대기
                    messagingTemplate.convertAndSend("/topic/messages", 
                        "새로운 데이터가 도착했습니다!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        websocketThread.start();
    }

    // 웹소켓 중지 메서드
    public synchronized void stopWebsocket() {
        isRunning = false;
        if (websocketThread != null) {
            websocketThread.interrupt();
        }
    }
}
