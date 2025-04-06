package me.alanton.exception.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessExceptionReason {
    MESSAGE_NOT_FOUND("Message not found", HttpStatus.NOT_FOUND);

    private final String code = this.getClass().getSimpleName();
    private final String message;
    private final HttpStatus status;
}
