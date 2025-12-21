package com.whqtker.polling_sse_ws.domain.http.controller;

import com.whqtker.polling_sse_ws.domain.http.dto.ChatMessageDto;
import com.whqtker.polling_sse_ws.domain.http.dto.LoginRequestDto;
import com.whqtker.polling_sse_ws.domain.http.dto.StatefulMessageDto;
import com.whqtker.polling_sse_ws.domain.http.service.HttpChatService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/http/stateful")
@RequiredArgsConstructor
public class StatefulChatController {

    private final HttpChatService httpChatService;
    private static final String SESSION_USER_KEY = "loggedInUser";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request, HttpSession session) {
        session.setAttribute(SESSION_USER_KEY, request.username());
        log.info("사용자 로그인: {} (Session ID: {})", request.username(), session.getId());
        return ResponseEntity.ok("로그인 성공: " + request.username());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        String username = (String) session.getAttribute(SESSION_USER_KEY);
        session.invalidate();
        log.info("사용자 로그아웃: {}", username);
        return ResponseEntity.ok("로그아웃 성공");
    }

    @PostMapping("/messages")
    public ResponseEntity<String> sendMessage(@RequestBody StatefulMessageDto request, HttpSession session) {
        String sender = (String) session.getAttribute(SESSION_USER_KEY);
        
        if (sender == null) {
            log.warn("로그인하지 않은 사용자가 메시지 전송 시도");
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }
        
        // 세션에서 sender를 가져와서 메시지 저장
        httpChatService.addMessage(new ChatMessageDto(sender, request.content()));
        log.info("Stateful 메시지 전송 - sender: {} (세션에서 자동 획득)", sender);
        return ResponseEntity.ok("메시지 전송 성공");
    }

    @GetMapping("/me")
    public ResponseEntity<String> whoAmI(HttpSession session) {
        String sender = (String) session.getAttribute(SESSION_USER_KEY);
        if (sender == null) {
            return ResponseEntity.status(401).body("로그인하지 않았습니다.");
        }
        return ResponseEntity.ok("현재 로그인: " + sender + " (Session ID: " + session.getId() + ")");
    }

    @GetMapping("/messages")
    public List<ChatMessageDto> getMessages() {
        return httpChatService.getMessages();
    }
}
