package com.kta.portal.admin.exception;

import com.kta.portal.admin.dto.ErrorDetail;
import com.kta.portal.admin.dto.ResponseDto;
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
        List<ErrorDetail> errors = List.of(ErrorDetail.builder()
                .message(e.getMessage())
                .code("BAD_REQUEST")
                .build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseDto<Object>> handleUnauthorized(UnauthorizedException e) {
        List<ErrorDetail> errors = List.of(ErrorDetail.builder()
                .message(e.getMessage())
                .code("UNAUTHORIZED")
                .build());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseDto<Object>> handleForbidden(ForbiddenException e) {
        List<ErrorDetail> errors = List.of(ErrorDetail.builder()
                .message(e.getMessage())
                .code("FORBIDDEN")
                .build());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto<Object>> handleNotFound(ResourceNotFoundException e) {
        List<ErrorDetail> errors = List.of(ErrorDetail.builder()
                .message(e.getMessage())
                .code("NOT_FOUND")
                .build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.error(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleGenericException(Exception e) {
        List<ErrorDetail> errors = List.of(ErrorDetail.builder()
                .message("Internal server error")
                .code("INTERNAL_SERVER_ERROR")
                .build());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.error(errors));
    }
}