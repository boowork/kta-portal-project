package com.kta.aidt.admin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kta.aidt.admin.dto.ErrorDetail;
import com.kta.aidt.admin.dto.ResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException authException) throws IOException, ServletException {
        
        ErrorDetail errorDetail = ErrorDetail.builder()
                .field("Authorization")
                .message("JWT token is missing or invalid")
                .build();

        ResponseDto<Object> responseDto = ResponseDto.builder()
                .success(false)
                .message("Authentication failed")
                .errors(Collections.singletonList(errorDetail))
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));
    }
}