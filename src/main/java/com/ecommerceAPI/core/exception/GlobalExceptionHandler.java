package com.ecommerceAPI.core.exception;

import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        return new ResponseEntity<>(new ErrorResponse(false, ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(false, ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> validationErrorList = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

        return new ResponseEntity<>(new ErrorResponse(false, validationErrorList.get(0), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Parameter '%s' must be of type '%s'", ex.getName(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : Msg.UNKNOWN);

        return new ResponseEntity<>(new ErrorResponse(false, message, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(new ErrorResponse(false, extractConstraintViolationMessage(ex), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    private String extractConstraintViolationMessage(DataIntegrityViolationException ex) {
        String message = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        System.err.println("Root Cause Message: " + message);

        if (message.contains("uk_role_email")) {
            return Msg.EMAIL_ALREADY_REGISTERED;
        } else if (message.contains("uk_role_phone")) {
            return Msg.PHONE_ALREADY_REGISTERED;
        } else if (message.contains("user_id")) {
            return Msg.USER_ALREADY_HAS_BASKET;
        } else if (message.contains("(name)=")) {
            return Msg.CATEGORY_NAME_ALREADY_EXISTS;
        }

        return Msg.UNKNOWN_CONSTRAINT_VIOLATION;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(false, ex.getMessage() != null ? ex.getMessage() : Msg.GENERIC_INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            if (invalidFormatException.getTargetType().isEnum()) {
                return new ResponseEntity<>(new ErrorResponse(false, Msg.ROLE_ERROR, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(new ErrorResponse(false, Msg.GENERIC_INTERNAL_ERROR, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(new ErrorResponse(false, ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
