package com.kta.aidt.admin.exception;

import com.kta.aidt.admin.dto.ErrorDetail;
import com.kta.aidt.admin.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDto<Object>> handleBadRequest(BadRequestException e) {
        List<ErrorDetail> errors = List.of(new ErrorDetail(e.getMessage(), "BAD_REQUEST"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseDto<Object>> handleUnauthorized(UnauthorizedException e) {
        List<ErrorDetail> errors = List.of(new ErrorDetail(e.getMessage(), "UNAUTHORIZED"));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseDto<Object>> handleForbidden(ForbiddenException e) {
        List<ErrorDetail> errors = List.of(new ErrorDetail(e.getMessage(), "FORBIDDEN"));
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto<Object>> handleNotFound(ResourceNotFoundException e) {
        List<ErrorDetail> errors = List.of(new ErrorDetail(e.getMessage(), "NOT_FOUND"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Object>> handleValidationErrors(MethodArgumentNotValidException e) {
        List<ErrorDetail> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(new ErrorDetail(error.getField(), error.getDefaultMessage(), "VALIDATION_ERROR"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleGenericException(Exception e) {
        List<ErrorDetail> errors = List.of(new ErrorDetail("Internal server error", "INTERNAL_SERVER_ERROR"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.error(errors));
    }
}