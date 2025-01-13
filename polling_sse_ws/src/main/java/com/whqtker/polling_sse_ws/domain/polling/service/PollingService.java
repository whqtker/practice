package com.whqtker.polling_sse_ws.domain.polling.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class PollingService {
    private final Random random = new Random();
    private final AtomicBoolean dataReady = new AtomicBoolean(false);

    // 생성자에서 데이터 준비 쓰레드 시작
    public PollingService() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500 + random.nextInt(1000)); // 0.5 ~ 1.5초 사이에 랜덤하게 데이터 준비
                    dataReady.set(true);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    // 데이터 준비 여부 리턴 후 false로 초기화
    public boolean isDataReady() {
        return dataReady.getAndSet(false);
    }
}