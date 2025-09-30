package com.kta.portal.admin.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private final String secretKey = "ThisIsAVerySecureSecretKeyForJWTTokenGenerationAndValidation2025";
    private final String issuer = "kta-portal-admin";
    private final Long accessTokenValidity = 86400000L; // 24 hours
    
    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtTokenProvider, "accessTokenValidity", accessTokenValidity);
        ReflectionTestUtils.setField(jwtTokenProvider, "refreshTokenValidity", 2592000000L);
        ReflectionTestUtils.setField(jwtTokenProvider, "issuer", issuer);
    }

    @Test
    void testGenerateToken_ContainsCorrectIssuer() {
        // Given
        UUID id = UUID.randomUUID();
        String userid = "testuser";
        String name = "Test User";
        
        // When
        String token = jwtTokenProvider.generateToken(id, userid, name);
        
        // Then
        assertNotNull(token);
        
        // Parse token and verify issuer
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        assertEquals(issuer, claims.getIssuer());
        assertEquals(id.toString(), claims.get("id", String.class));
        assertEquals(userid, claims.get("userid"));
        assertEquals(name, claims.get("name"));
    }

    @Test
    void testValidateToken_WithCorrectIssuer_ReturnsTrue() {
        // Given
        String token = jwtTokenProvider.generateToken(UUID.randomUUID(), "user", "User");
        
        // When
        boolean isValid = jwtTokenProvider.validateToken(token);
        
        // Then
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_WithIncorrectIssuer_ReturnsFalse() {
        // Given - Create token with different issuer
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        String tokenWithWrongIssuer = Jwts.builder()
                .claim("id", UUID.randomUUID().toString())
                .claim("userid", "user")
                .claim("name", "User")
                .setIssuer("kta-portal-school") // Wrong issuer
                .signWith(key)
                .compact();
        
        // When
        boolean isValid = jwtTokenProvider.validateToken(tokenWithWrongIssuer);
        
        // Then
        assertFalse(isValid);
    }

    @Test
    void testValidateToken_WithNoIssuer_ReturnsFalse() {
        // Given - Create token without issuer
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        String tokenWithoutIssuer = Jwts.builder()
                .claim("id", UUID.randomUUID().toString())
                .claim("userid", "user")
                .claim("name", "User")
                // No issuer set
                .signWith(key)
                .compact();
        
        // When
        boolean isValid = jwtTokenProvider.validateToken(tokenWithoutIssuer);
        
        // Then
        assertFalse(isValid);
    }

    @Test
    void testGetIssuerFromToken_ReturnsCorrectIssuer() {
        // Given
        String token = jwtTokenProvider.generateToken(UUID.randomUUID(), "user", "User");
        
        // When
        String extractedIssuer = jwtTokenProvider.getIssuerFromToken(token);
        
        // Then
        assertEquals(issuer, extractedIssuer);
    }

    @Test
    void testValidateToken_WithExpiredToken_ReturnsFalse() {
        // Given - Create expired token
        ReflectionTestUtils.setField(jwtTokenProvider, "accessTokenValidity", -1000L); // Negative means expired
        String expiredToken = jwtTokenProvider.generateToken(UUID.randomUUID(), "user", "User");
        
        // Reset to normal validity for validation
        ReflectionTestUtils.setField(jwtTokenProvider, "accessTokenValidity", accessTokenValidity);
        
        // When
        boolean isValid = jwtTokenProvider.validateToken(expiredToken);
        
        // Then
        assertFalse(isValid);
    }

    @Test
    void testValidateToken_WithInvalidSignature_ReturnsFalse() {
        // Given - Create token with different secret
        SecretKey wrongKey = Keys.hmacShaKeyFor("AnotherSecretKeyThatIsCompletelyDifferentFromOriginal123456789".getBytes());
        String tokenWithWrongSignature = Jwts.builder()
                .claim("id", UUID.randomUUID().toString())
                .claim("userid", "user")
                .claim("name", "User")
                .setIssuer(issuer)
                .signWith(wrongKey)
                .compact();
        
        // When
        boolean isValid = jwtTokenProvider.validateToken(tokenWithWrongSignature);
        
        // Then
        assertFalse(isValid);
    }

    @Test
    void testGetUserIdFromToken() {
        // Given
        String expectedUserId = "testuser123";
        String token = jwtTokenProvider.generateToken(UUID.randomUUID(), expectedUserId, "Test User");
        
        // When
        String actualUserId = jwtTokenProvider.getUserIdFromToken(token);
        
        // Then
        assertEquals(expectedUserId, actualUserId);
    }


    @Test
    void testMultipleServiceIssuers() {
        // Test that tokens from different services are properly rejected
        String[] serviceIssuers = {
            "kta-portal-school",
            "kta-portal-front",
            "kta-portal-api"
        };
        
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        
        for (String serviceIssuer : serviceIssuers) {
            // Create token with different service issuer
            String token = Jwts.builder()
                    .claim("id", UUID.randomUUID().toString())
                    .claim("userid", "user")
                    .claim("name", "User")
                    .setIssuer(serviceIssuer)
                    .signWith(key)
                    .compact();
            
            // Should be invalid for kta-portal-admin
            boolean isValid = jwtTokenProvider.validateToken(token);
            assertFalse(isValid, "Token with issuer " + serviceIssuer + " should be invalid");
        }
    }
}