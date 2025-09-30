package com.kta.portal.admin.feature.api.auth;

import com.kta.portal.admin.dto.ErrorDetail;
import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.feature.api.auth.model.RefreshToken;
import com.kta.portal.admin.security.JwtTokenProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PostLoginController {

    private final PostLoginService postLoginService;

    @PostMapping("/api/login")
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
}


@Data
@lombok.Builder
class PostLoginDaoResponseDto {
    private UUID id;
    private String userid;
    private String password;
    private String name;
}

@Service
@RequiredArgsConstructor
class PostLoginService {

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
                    user.getId(), user.getUserid(), user.getName());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

            PostLoginHttpResponseDto response = PostLoginHttpResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getToken())
                    .userid(user.getUserid())
                    .name(user.getName())
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

@Repository
@RequiredArgsConstructor
class PostLoginDao {

    private final JdbcTemplate jdbcTemplate;

    public PostLoginDaoResponseDto findUserByUserid(String userid) {
        String sql = "SELECT id, userid, password, name FROM portal_users WHERE userid = ?";
        Map<String, Object> row = jdbcTemplate.queryForMap(sql, userid);

        return PostLoginDaoResponseDto.builder()
                .id((UUID) row.get("id"))
                .userid((String) row.get("userid"))
                .password((String) row.get("password"))
                .name((String) row.get("name"))
                .build();
    }
}