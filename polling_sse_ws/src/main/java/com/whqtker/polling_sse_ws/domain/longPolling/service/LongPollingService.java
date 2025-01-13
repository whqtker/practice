package com.whqtker.polling_sse_ws.domain.longPolling.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class LongPollingService {
    private static final Logger logger = LoggerFactory.getLogger(LongPollingService.class);
    private final Random random = new Random();
    private final AtomicBoolean dataReady = new AtomicBoolean(false);

    public LongPollingService() {
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

    public boolean isDataReady() {
        return dataReady.getAndSet(false);
    }

    public String longPolling() throws InterruptedException {
        while (true) {
            logger.info("데이터가 준비되었는지 확인해야지.");
            if (isDataReady()) {
                logger.info("데이터 준비 완료! 클라이언트에게 전송해야지.");
                logger.info("==========");
                return "데이터 준비되었습니다!";
            } else {
                logger.info("아직 데이터가 준비되지 않았어...");
                Thread.sleep(100); // 0.1초마다 데이터 준비 여부 확인
            }
            logger.info("==========");
        }
    }
}