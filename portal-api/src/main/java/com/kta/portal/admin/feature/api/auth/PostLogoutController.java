package com.kta.portal.admin.feature.api.auth;

import com.kta.portal.admin.dto.ResponseDto;
import lombok.Data;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostLogoutController {

    private final PostLogoutService postLogoutService;

    @PostMapping("/api/logout")
    public ResponseDto<PostLogoutHttpResponseDto> logout() {
        return postLogoutService.logout();
    }
}

@Service
@RequiredArgsConstructor
class PostLogoutService {
    
    private final PostLogoutDao postLogoutDao;
    private final RefreshTokenService refreshTokenService;

    public ResponseDto<PostLogoutHttpResponseDto> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            String userid = authentication.getName();
            
            try {
                PostLogoutDaoResponseDto user = postLogoutDao.findUserByUserid(userid);
                refreshTokenService.deleteByUserId(user.getId());
                
                PostLogoutHttpResponseDto response = PostLogoutHttpResponseDto.builder()
                    .message("Logged out successfully")
                    .build();
                
                return ResponseDto.success(response);
                
            } catch (Exception e) {
                // User not found or already logged out
            }
        }
        
        PostLogoutHttpResponseDto response = PostLogoutHttpResponseDto.builder()
            .message("Already logged out")
            .build();
        
        return ResponseDto.success(response);
    }
}

@Repository
@RequiredArgsConstructor
class PostLogoutDao {
    
    private final JdbcTemplate jdbcTemplate;
    
    public PostLogoutDaoResponseDto findUserByUserid(String userid) {
        String sql = "SELECT id FROM users WHERE userid = ?";
        Map<String, Object> row = jdbcTemplate.queryForMap(sql, userid);
        
        return PostLogoutDaoResponseDto.builder()
            .id(((Number) row.get("id")).longValue())
            .build();
    }
}

@Data
@Builder
class PostLogoutHttpResponseDto {
    private String message;
}

@Data
@Builder
class PostLogoutDaoResponseDto {
    private Long id;
}