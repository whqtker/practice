package com.whqtker.polling_sse_ws.domain.longPolling.controller;

import com.whqtker.polling_sse_ws.domain.longPolling.service.LongPollingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LongPollingController {
    private final LongPollingService longPollingService;

    @GetMapping("/longPolling")
    public String longPolling() {
        return "longPolling";
    }

    @GetMapping("/longPolling/data")
    @ResponseBody
    public String longPollingData() throws InterruptedException {
        return longPollingService.longPolling();
    }
}
