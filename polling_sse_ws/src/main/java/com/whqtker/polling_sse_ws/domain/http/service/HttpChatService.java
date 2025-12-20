package com.whqtker.polling_sse_ws.domain.http.service;

import com.whqtker.polling_sse_ws.domain.http.dto.ChatMessageDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HttpChatService {
    private final List<ChatMessageDto> messages = Collections.synchronizedList(new ArrayList<>());

    public void addMessage(ChatMessageDto message) {
        messages.add(message);
    }

    public List<ChatMessageDto> getMessages() {
        return new ArrayList<>(messages);
    }
}
