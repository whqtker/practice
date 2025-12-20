package com.whqtker.polling_sse_ws.domain.longPolling.service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LongPollingService {
    private final Random random = new Random();
    private final AtomicBoolean dataReady = new AtomicBoolean(false);

    public LongPollingService() {
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

    public boolean isDataReady() {
        return dataReady.getAndSet(false);
    }

    public String longPolling() throws InterruptedException {
        while (true) {
            log.debug("데이터 준비 여부 확인 중...");
            if (isDataReady()) {
                log.info("데이터 준비 완료! 클라이언트에게 전송");
                return "데이터 준비되었습니다!";
            }
            Thread.sleep(100); // 0.1초마다 데이터 준비 여부 확인
        }
    }
}
