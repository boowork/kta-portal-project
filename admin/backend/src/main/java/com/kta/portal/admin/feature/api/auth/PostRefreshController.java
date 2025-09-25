package com.kta.portal.admin.feature.api.auth;

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.feature.api.auth.model.RefreshToken;
import com.kta.portal.admin.security.JwtTokenProvider;
import com.kta.portal.admin.dto.ErrorDetail;
import lombok.Data;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostRefreshController {

    private final PostRefreshService postRefreshService;

    @PostMapping("/api/refresh")
    public ResponseDto<PostRefreshHttpResponseDto> refreshToken(@Valid @RequestBody PostRefreshHttpRequestDto request) {
        return postRefreshService.refreshToken(request);
    }
}

@Service
@RequiredArgsConstructor
class PostRefreshService {
    
    private final PostRefreshDao postRefreshDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    public ResponseDto<PostRefreshHttpResponseDto> refreshToken(PostRefreshHttpRequestDto request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .filter(refreshTokenService::validateRefreshToken)
                .map(token -> {
                    PostRefreshDaoResponseDto user = postRefreshDao.findUserById(token.getUserId());
                    
                    String newAccessToken = jwtTokenProvider.generateToken(
                        user.getId(), user.getUserid(), user.getName());
                    
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getId());
                    
                    PostRefreshHttpResponseDto response = PostRefreshHttpResponseDto.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken.getToken())
                        .build();
                    
                    return ResponseDto.success(response);
                })
                .orElse(ResponseDto.error(Collections.singletonList(
                    ErrorDetail.builder()
                        .field("refreshToken")
                        .message("Invalid or expired refresh token")
                        .code("INVALID_TOKEN")
                        .build()
                )));
    }
}

@Repository
@RequiredArgsConstructor
class PostRefreshDao {
    
    private final JdbcTemplate jdbcTemplate;
    
    public PostRefreshDaoResponseDto findUserById(Long userId) {
        String sql = "SELECT id, userid, name FROM users WHERE id = ?";
        Map<String, Object> row = jdbcTemplate.queryForMap(sql, userId);
        
        return PostRefreshDaoResponseDto.builder()
            .id(((Number) row.get("id")).longValue())
            .userid((String) row.get("userid"))
            .name((String) row.get("name"))
            .build();
    }
}

@Data
class PostRefreshHttpRequestDto {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}

@Data
@lombok.Builder
class PostRefreshHttpResponseDto {
    private String accessToken;
    private String refreshToken;
}

@Data
@Builder
class PostRefreshDaoResponseDto {
    private Long id;
    private String userid;
    private String name;
}