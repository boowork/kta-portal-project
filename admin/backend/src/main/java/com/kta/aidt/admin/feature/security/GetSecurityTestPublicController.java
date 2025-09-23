package com.kta.aidt.admin.feature.security;

import com.kta.aidt.admin.dto.ResponseDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/security-test")
@RequiredArgsConstructor
public class GetSecurityTestPublicController {

    private final GetSecurityTestPublicService getSecurityTestPublicService;

    @GetMapping("/public")
    public ResponseDto<GetSecurityTestPublicHttpResponseDto> publicEndpoint() {
        return getSecurityTestPublicService.getPublicMessage();
    }
}

@Service
class GetSecurityTestPublicService {

    public ResponseDto<GetSecurityTestPublicHttpResponseDto> getPublicMessage() {
        GetSecurityTestPublicHttpResponseDto response = GetSecurityTestPublicHttpResponseDto.builder()
            .message("This is a public endpoint")
            .build();
            
        return ResponseDto.success(response);
    }
}

@Data
@lombok.Builder
class GetSecurityTestPublicHttpResponseDto {
    private String message;
}