package com.whqtker.polling_sse_ws.domain.polling.controller;

import com.whqtker.polling_sse_ws.domain.polling.dto.PollingResponseDto;
import com.whqtker.polling_sse_ws.domain.polling.service.PollingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PollingController {
    private final PollingService pollingService;


    @GetMapping("/polling")
    public PollingResponseDto pollingData() {
        return new PollingResponseDto(pollingService.isDataReady());
    }
}