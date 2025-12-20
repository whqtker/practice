package com.whqtker.polling_sse_ws.domain.sse.service;

import com.whqtker.polling_sse_ws.domain.sse.controller.SseEmitters;
import com.whqtker.polling_sse_ws.domain.sse.dto.SseEventDto;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {
    private final Random random = new Random();
    private final SseEmitters sseEmitters;
    private Thread sseThread;
    private volatile boolean isRunning = false;

    public synchronized void startSse() {
        if (isRunning) {
            return;
        }

        isRunning = true;
        sseThread = new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(500 + random.nextInt(1000));
                    log.debug("SSE 데이터 생성 완료, 브로드캐스트 중...");
                    sseEmitters.broadcast("newData", new SseEventDto("새로운 데이터가 도착했습니다!"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        sseThread.start();
        log.info("SSE Producer 시작됨");
    }

    public synchronized void stopSse() {
        isRunning = false;
        if (sseThread != null) {
            sseThread.interrupt();
        }
        log.info("SSE Producer 중지됨");
    }
}

