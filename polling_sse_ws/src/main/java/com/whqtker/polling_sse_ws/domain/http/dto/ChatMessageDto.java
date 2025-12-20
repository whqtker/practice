package com.whqtker.polling_sse_ws.domain.http.dto;

public record ChatMessageDto(
    String sender,
    String content
) {}
