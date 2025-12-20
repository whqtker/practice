package com.whqtker.polling_sse_ws.global.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class StompLoggingInterceptor implements ChannelInterceptor {

    private static final Logger log = LoggerFactory.getLogger(StompLoggingInterceptor.class);

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        if (command != null) {
            String sessionId = accessor.getSessionId();
            String destination = accessor.getDestination();
            Object payload = message.getPayload();

            log.info(">>> STOMP [{}] | Session: {} | Destination: {} | Headers: {} | Payload: {}",
                    command,
                    sessionId,
                    destination,
                    accessor.toNativeHeaderMap(),
                    payload instanceof byte[] ? new String((byte[]) payload) : payload);
        }

        return message;
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        if (ex != null) {
            log.error("<<< STOMP Error: {}", ex.getMessage());
        }
    }
}
