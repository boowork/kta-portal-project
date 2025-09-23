package com.kta.aidt.admin.feature.auth;

import com.kta.aidt.admin.dto.ResponseDto;
import com.kta.aidt.admin.feature.auth.model.RefreshToken;
import com.kta.aidt.admin.dto.ErrorDetail;
import com.kta.aidt.admin.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class PostLoginService {
    
    private final PostLoginDao postLoginDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public ResponseDto<PostLoginHttpResponseDto> login(PostLoginHttpRequestDto request) {
        try {
            PostLoginDaoResponseDto user = postLoginDao.findUserByUserid(request.getUserid());
            
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseDto.error(Collections.singletonList(
                    ErrorDetail.builder()
                        .field("password")
                        .message("Invalid credentials")
                        .code("INVALID_CREDENTIALS")
                        .build()
                ));
            }
            
            String accessToken = jwtTokenProvider.generateToken(
                user.getId(), user.getUserid(), user.getName(), user.getRole());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
            
            PostLoginHttpResponseDto response = PostLoginHttpResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .userid(user.getUserid())
                .name(user.getName())
                .role(user.getRole())
                .build();
            
            return ResponseDto.success(response);
            
        } catch (Exception e) {
            return ResponseDto.error(Collections.singletonList(
                ErrorDetail.builder()
                    .field("userid")
                    .message("Invalid credentials")
                    .code("INVALID_CREDENTIALS")
                    .build()
            ));
        }
    }
}