package com.whqtker.polling_sse_ws.domain.sse.service;

import com.whqtker.polling_sse_ws.domain.sse.controller.SseEmitters;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class SseService {
    private static final Logger logger = LoggerFactory.getLogger(SseService.class);
    private final Random random = new Random();
    private final AtomicBoolean dataReady = new AtomicBoolean(false);
    private final SseEmitters sseEmitters;
    private Thread dataThread;

    @PostConstruct
    public void init() {
        dataThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(500 + random.nextInt(1000)); // 0.5 ~ 1.5초 사이에 랜덤하게 데이터 준비
                    dataReady.set(true);
                    notifyClients();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        dataThread.start();
    }

    public boolean isDataReady() {
        return dataReady.getAndSet(false);
    }

    @Async
    public void notifyClients() {
        if (isDataReady()) {
            logger.info("데이터 준비 완료! 클라이언트에게 전송해야지.");
            sseEmitters.noti("data", Map.of("message", "데이터가 준비되었습니다!"));
        } else {
            logger.info("데이터가 준비되지 않았어...");
        }
    }
}