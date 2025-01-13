package com.whqtker.polling_sse_ws.domain.polling.controller;

import com.whqtker.polling_sse_ws.domain.polling.service.PollingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PollingController {
    private final PollingService pollingService;

    @GetMapping("/polling")
    public String polling() {
        return "polling";
    }

    @GetMapping("/polling/data")
    @ResponseBody
    public String pollingData() {
        if (pollingService.isDataReady()) {
            return "아직입니다.";
        } else {
            return "데이터 준비되었습니다!";
        }
    }
}