package com.whqtker.polling_sse_ws.domain.polling.service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Service;

@Service
public class PollingService {
    private final Random random = new Random();
    private final AtomicBoolean dataReady = new AtomicBoolean(false);

    // 생성자에서 데이터 준비 쓰레드 시작
    public PollingService() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000 + random.nextInt(5000)); // 5 ~ 10초 사이에 랜덤하게 데이터 준비
                    dataReady.set(true);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    // 데이터 준비 여부에 따라 메시지 리턴 후 false로 초기화
    public String isDataReady() {
        if (dataReady.getAndSet(false)) {
            return "데이터 준비되었습니다!";
        } else {
            return "아직 데이터가 준비되지 않았습니다.";
        }
    }
}