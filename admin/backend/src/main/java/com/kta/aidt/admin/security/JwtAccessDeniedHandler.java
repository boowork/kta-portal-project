package com.kta.aidt.admin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kta.aidt.admin.dto.ErrorDetail;
import com.kta.aidt.admin.dto.ResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                      AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        ErrorDetail errorDetail = ErrorDetail.builder()
                .field("Authorization")
                .message("Insufficient privileges to access this resource")
                .build();

        ResponseDto<Object> responseDto = ResponseDto.builder()
                .success(false)
                .message("Access denied")
                .errors(Collections.singletonList(errorDetail))
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));
    }
}