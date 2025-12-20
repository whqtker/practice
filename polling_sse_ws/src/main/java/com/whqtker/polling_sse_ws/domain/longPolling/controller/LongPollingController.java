package com.whqtker.polling_sse_ws.domain.longPolling.controller;

import com.whqtker.polling_sse_ws.domain.longPolling.dto.LongPollingResponseDto;
import com.whqtker.polling_sse_ws.domain.longPolling.service.LongPollingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LongPollingController {
    private final LongPollingService longPollingService;

    @GetMapping("/long-polling")
    public LongPollingResponseDto longPollingData() throws InterruptedException {
        return new LongPollingResponseDto(longPollingService.longPolling());
    }
}
