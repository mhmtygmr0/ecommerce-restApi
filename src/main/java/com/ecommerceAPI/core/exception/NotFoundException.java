package com.ecommerceAPI.core.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message, String entity) {
        super(entity + " " + message);
    }

    public NotFoundException(String message) {
        super(message);
    }
}