package com.whqtker.polling_sse_ws.domain.sse.service;

import com.whqtker.polling_sse_ws.domain.sse.controller.SseEmitters;
import com.whqtker.polling_sse_ws.global.ut.Ut;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class SseService {
    private static final Logger logger = LoggerFactory.getLogger(SseService.class);
    private final Random random = new Random();
    private final SseEmitters sseEmitters;
    private Thread sseThread;
    private volatile boolean isRunning = false;

    // SSE 시작 메서드
    public synchronized void startSse() {
        if (isRunning) return;
        
        isRunning = true;
        sseThread = new Thread(() -> {
            while (isRunning) {
                try {
                    logger.info("데이터 생성 중...");
                    Thread.sleep(500 + random.nextInt(1000));
                    logger.info("데이터 생성 완료! 클라이언트에 전송해야지.");
                    sseEmitters.noti("newData", Ut.mapOf(
                            "message", "새로운 데이터가 도착했습니다!"
                    ));
                    logger.info("==========");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        sseThread.start();
    }

    // SSE 중지 메서드
    public synchronized void stopSse() {
        isRunning = false;
        if (sseThread != null) {
            sseThread.interrupt();
        }
    }
}
