package com.kta.aidt.admin.feature.auth;

import com.kta.aidt.admin.dto.ResponseDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostLoginController {

    private final PostLoginService postLoginService;

    @PostMapping("/login")
    public ResponseDto<PostLoginHttpResponseDto> login(@Valid @RequestBody PostLoginHttpRequestDto request) {
        return postLoginService.login(request);
    }
}

@Data
class PostLoginHttpRequestDto {
    @NotBlank(message = "User ID is required")
    private String userid;
    
    @NotBlank(message = "Password is required")
    private String password;
}

@Data
@lombok.Builder
class PostLoginHttpResponseDto {
    private String accessToken;
    private String refreshToken;
    private String userid;
    private String name;
    private String role;
}

@Data
@lombok.Builder
class PostLoginDaoResponseDto {
    private Long id;
    private String userid;
    private String password;
    private String name;
    private String role;
}