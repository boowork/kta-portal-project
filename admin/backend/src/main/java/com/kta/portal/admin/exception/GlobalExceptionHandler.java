package com.kta.portal.admin.exception;

import com.kta.portal.admin.dto.ErrorDetail;
import com.kta.portal.admin.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDto<Object>> handleBadRequest(BadRequestException e) {
        log.warn("Bad request error occurred: message={}", e.getMessage(), e);
        List<ErrorDetail> errors = List.of(ErrorDetail.builder()
                .message(e.getMessage())
                .code("BAD_REQUEST")
                .build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseDto<Object>> handleUnauthorized(UnauthorizedException e) {
        log.warn("Unauthorized access attempt: message={}", e.getMessage());
        List<ErrorDetail> errors = List.of(ErrorDetail.builder()
                .message(e.getMessage())
                .code("UNAUTHORIZED")
                .build());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseDto<Object>> handleForbidden(ForbiddenException e) {
        log.warn("Forbidden access attempt: message={}", e.getMessage());
        List<ErrorDetail> errors = List.of(ErrorDetail.builder()
                .message(e.getMessage())
                .code("FORBIDDEN")
                .build());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto<Object>> handleNotFound(ResourceNotFoundException e) {
        log.info("Resource not found: message={}", e.getMessage());
        List<ErrorDetail> errors = List.of(ErrorDetail.builder()
                .message(e.getMessage())
                .code("NOT_FOUND")
                .build());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Object>> handleValidationErrors(MethodArgumentNotValidException e) {
        List<ErrorDetail> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(ErrorDetail.builder()
                    .field(error.getField())
                    .message(error.getDefaultMessage())
                    .code("VALIDATION_ERROR")
                    .build());
        }
        log.warn("Validation failed: errors={}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleGenericException(Exception e) {
        log.error("Unexpected error occurred: type={}, message={}", e.getClass().getSimpleName(), e.getMessage(), e);
        List<ErrorDetail> errors = List.of(ErrorDetail.builder()
                .message("Internal server error")
                .code("INTERNAL_SERVER_ERROR")
                .build());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.error(errors));
    }
}