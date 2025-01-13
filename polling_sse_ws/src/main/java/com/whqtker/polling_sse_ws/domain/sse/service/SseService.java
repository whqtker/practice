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

    @PostConstruct
    public void init() {
        // 백그라운드 스레드에서 주기적으로 데이터 생성 및 전송
        new Thread(() -> {
            while (true) {
                try {
                    // 0.5 ~ 1.5초 사이 랜덤 대기
                    Thread.sleep(500 + random.nextInt(1000));
                    logger.info("데이터 생성 완료! 클라이언트에 전송해야지.");
                    // 모든 클라이언트에게 데이터 전송
                    sseEmitters.noti("newData", Ut.mapOf(
                            "message", "새로운 데이터가 도착했습니다!"
                    ));
                    logger.info("==========");
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }
}
