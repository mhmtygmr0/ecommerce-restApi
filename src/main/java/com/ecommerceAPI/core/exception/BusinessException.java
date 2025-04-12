package com.ecommerceAPI.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public BusinessException(String message) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }
} 