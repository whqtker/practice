package com.whqtker.polling_sse_ws.domain.sse.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@Slf4j
public class SseEmitters {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter add(SseEmitter emitter) {
        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(emitter::complete);

        return emitter;
    }

    public void broadcast(String eventName) {
        broadcast(eventName, Map.of());
    }

    public void broadcast(String eventName, Object data) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name(eventName)
                                .data(data)
                );
            } catch (ClientAbortException e) {
                log.debug("Client aborted connection: {}", e.getMessage());
                emitters.remove(emitter);
            } catch (IOException e) {
                log.warn("Failed to send SSE event, removing emitter: {}", e.getMessage());
                emitters.remove(emitter);
            }
        });
    }
}

