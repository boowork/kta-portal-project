package com.kta.aidt.admin.feature.security;

import com.kta.aidt.admin.dto.ResponseDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/security-test")
@RequiredArgsConstructor
public class GetSecurityTestUserController {

    private final GetSecurityTestUserService getSecurityTestUserService;

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseDto<GetSecurityTestUserHttpResponseDto> userEndpoint() {
        return getSecurityTestUserService.getUserMessage();
    }
}

@Service
class GetSecurityTestUserService {

    public ResponseDto<GetSecurityTestUserHttpResponseDto> getUserMessage() {
        GetSecurityTestUserHttpResponseDto response = GetSecurityTestUserHttpResponseDto.builder()
            .message("This endpoint requires USER role")
            .build();
            
        return ResponseDto.success(response);
    }
}

@Data
@lombok.Builder
class GetSecurityTestUserHttpResponseDto {
    private String message;
}