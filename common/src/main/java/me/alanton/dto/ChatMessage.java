package me.alanton.dto;

import lombok.Builder;
import me.alanton.enums.MessageType;

import java.time.Instant;

@Builder
public record ChatMessage(
        String senderId,
        String receiverId,
        String content,
        Instant timestamp,
        MessageType type
) {
}
