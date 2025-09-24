package com.kta.portal.admin.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

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
        Long id = 1L;
        String userid = "testuser";
        String name = "Test User";
        String role = "USER";
        
        // When
        String token = jwtTokenProvider.generateToken(id, userid, name, role);
        
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
        assertEquals(id, claims.get("id", Long.class));
        assertEquals(userid, claims.get("userid"));
        assertEquals(name, claims.get("name"));
        assertEquals(role, claims.get("role"));
    }

    @Test
    void testValidateToken_WithCorrectIssuer_ReturnsTrue() {
        // Given
        String token = jwtTokenProvider.generateToken(1L, "user", "User", "USER");
        
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
                .claim("id", 1L)
                .claim("userid", "user")
                .claim("name", "User")
                .claim("role", "USER")
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
                .claim("id", 1L)
                .claim("userid", "user")
                .claim("name", "User")
                .claim("role", "USER")
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
        String token = jwtTokenProvider.generateToken(1L, "user", "User", "USER");
        
        // When
        String extractedIssuer = jwtTokenProvider.getIssuerFromToken(token);
        
        // Then
        assertEquals(issuer, extractedIssuer);
    }

    @Test
    void testValidateToken_WithExpiredToken_ReturnsFalse() {
        // Given - Create expired token
        ReflectionTestUtils.setField(jwtTokenProvider, "accessTokenValidity", -1000L); // Negative means expired
        String expiredToken = jwtTokenProvider.generateToken(1L, "user", "User", "USER");
        
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
                .claim("id", 1L)
                .claim("userid", "user")
                .claim("name", "User")
                .claim("role", "USER")
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
        String token = jwtTokenProvider.generateToken(1L, expectedUserId, "Test User", "USER");
        
        // When
        String actualUserId = jwtTokenProvider.getUserIdFromToken(token);
        
        // Then
        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    void testGetRoleFromToken() {
        // Given
        String expectedRole = "ADMIN";
        String token = jwtTokenProvider.generateToken(1L, "admin", "Admin User", expectedRole);
        
        // When
        String actualRole = jwtTokenProvider.getRoleFromToken(token);
        
        // Then
        assertEquals(expectedRole, actualRole);
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
                    .claim("id", 1L)
                    .claim("userid", "user")
                    .claim("name", "User")
                    .claim("role", "USER")
                    .setIssuer(serviceIssuer)
                    .signWith(key)
                    .compact();
            
            // Should be invalid for kta-portal-admin
            boolean isValid = jwtTokenProvider.validateToken(token);
            assertFalse(isValid, "Token with issuer " + serviceIssuer + " should be invalid");
        }
    }
}