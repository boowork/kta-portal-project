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
public class GetSecurityTestAdminController {

    private final GetSecurityTestAdminService getSecurityTestAdminService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<GetSecurityTestAdminHttpResponseDto> adminEndpoint() {
        return getSecurityTestAdminService.getAdminMessage();
    }
}

@Service
class GetSecurityTestAdminService {

    public ResponseDto<GetSecurityTestAdminHttpResponseDto> getAdminMessage() {
        GetSecurityTestAdminHttpResponseDto response = GetSecurityTestAdminHttpResponseDto.builder()
            .message("This endpoint requires ADMIN role")
            .build();
            
        return ResponseDto.success(response);
    }
}

@Data
@lombok.Builder
class GetSecurityTestAdminHttpResponseDto {
    private String message;
}