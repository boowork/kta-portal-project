package com.kta.portal.admin.feature.api.auth;

import com.kta.portal.admin.feature.api.auth.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<RefreshToken> rowMapper = new RefreshTokenRowMapper();
    
    public RefreshToken save(RefreshToken refreshToken) {
        if (refreshToken.getId() == null) {
            return insert(refreshToken);
        } else {
            return update(refreshToken);
        }
    }
    
    private RefreshToken insert(RefreshToken refreshToken) {
        String sql = "INSERT INTO refresh_tokens (user_id, token, expires_at, created_at) VALUES (?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, refreshToken.getUserId());
            ps.setString(2, refreshToken.getToken());
            ps.setTimestamp(3, Timestamp.valueOf(refreshToken.getExpiresAt()));
            ps.setTimestamp(4, Timestamp.valueOf(refreshToken.getCreatedAt()));
            return ps;
        }, keyHolder);
        
        Long id = keyHolder.getKey().longValue();
        return RefreshToken.builder()
                .id(id)
                .userId(refreshToken.getUserId())
                .token(refreshToken.getToken())
                .expiresAt(refreshToken.getExpiresAt())
                .createdAt(refreshToken.getCreatedAt())
                .build();
    }
    
    private RefreshToken update(RefreshToken refreshToken) {
        String sql = "UPDATE refresh_tokens SET user_id = ?, token = ?, expires_at = ?, created_at = ? WHERE id = ?";
        
        jdbcTemplate.update(sql,
                refreshToken.getUserId(),
                refreshToken.getToken(),
                Timestamp.valueOf(refreshToken.getExpiresAt()),
                Timestamp.valueOf(refreshToken.getCreatedAt()),
                refreshToken.getId());
        
        return refreshToken;
    }
    
    public Optional<RefreshToken> findByToken(String token) {
        String sql = "SELECT * FROM refresh_tokens WHERE token = ?";
        
        return jdbcTemplate.query(sql, rowMapper, token)
                .stream()
                .findFirst();
    }
    
    public Optional<RefreshToken> findByUserId(Long userId) {
        String sql = "SELECT * FROM refresh_tokens WHERE user_id = ?";
        
        return jdbcTemplate.query(sql, rowMapper, userId)
                .stream()
                .findFirst();
    }
    
    public void deleteByUserId(Long userId) {
        String sql = "DELETE FROM refresh_tokens WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }
    
    public void delete(RefreshToken refreshToken) {
        String sql = "DELETE FROM refresh_tokens WHERE id = ?";
        jdbcTemplate.update(sql, refreshToken.getId());
    }
    
    public void deleteExpiredTokens(LocalDateTime now) {
        String sql = "DELETE FROM refresh_tokens WHERE expires_at < ?";
        jdbcTemplate.update(sql, Timestamp.valueOf(now));
    }
    
    private static class RefreshTokenRowMapper implements RowMapper<RefreshToken> {
        @Override
        public RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
            return RefreshToken.builder()
                    .id(rs.getLong("id"))
                    .userId(rs.getLong("user_id"))
                    .token(rs.getString("token"))
                    .expiresAt(rs.getTimestamp("expires_at").toLocalDateTime())
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .build();
        }
    }
}