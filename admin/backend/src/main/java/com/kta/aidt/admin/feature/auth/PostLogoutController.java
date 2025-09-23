package com.kta.aidt.admin.feature.auth;

import com.kta.aidt.admin.dto.ResponseDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostLogoutController {

    private final PostLogoutService postLogoutService;

    @PostMapping("/logout")
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
@lombok.Builder
class PostLogoutHttpResponseDto {
    private String message;
}

@Data
@lombok.Builder
class PostLogoutDaoResponseDto {
    private Long id;
}