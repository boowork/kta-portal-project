package com.kta.aidt.admin.feature.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PostLoginDao {
    
    private final JdbcTemplate jdbcTemplate;
    
    public PostLoginDaoResponseDto findUserByUserid(String userid) {
        String sql = "SELECT id, userid, password, name, role FROM users WHERE userid = ?";
        Map<String, Object> row = jdbcTemplate.queryForMap(sql, userid);
        
        return PostLoginDaoResponseDto.builder()
            .id(((Number) row.get("id")).longValue())
            .userid((String) row.get("userid"))
            .password((String) row.get("password"))
            .name((String) row.get("name"))
            .role((String) row.get("role"))
            .build();
    }
}