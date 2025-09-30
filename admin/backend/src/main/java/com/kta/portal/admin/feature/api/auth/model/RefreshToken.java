package com.kta.portal.admin.feature.api.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    
    private UUID id;
    private UUID userId;
    private String token;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}