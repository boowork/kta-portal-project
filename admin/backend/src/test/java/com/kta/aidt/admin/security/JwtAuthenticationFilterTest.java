package com.kta.aidt.admin.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JwtTokenProvider jwtTokenProvider;
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;
    
    @Mock
    private FilterChain filterChain;
    
    private final String secretKey = "ThisIsAVerySecureSecretKeyForJWTTokenGenerationAndValidation2025";
    private final String issuer = "kta-portal-admin";
    
    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtTokenProvider, "accessTokenValidity", 86400000L);
        ReflectionTestUtils.setField(jwtTokenProvider, "issuer", issuer);
        
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider);
        SecurityContextHolder.clearContext();
    }
    
    @Test
    void testDoFilterInternal_WithValidToken_SetsAuthentication() throws Exception {
        // Given
        String token = jwtTokenProvider.generateToken(1L, "testuser", "Test User", "USER");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        
        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals("testuser", authentication.getName());
        assertTrue(authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        
        verify(filterChain, times(1)).doFilter(request, response);
    }
    
    @Test
    void testDoFilterInternal_WithInvalidIssuer_DoesNotSetAuthentication() throws Exception {
        // Given - Create token with wrong issuer
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        String tokenWithWrongIssuer = Jwts.builder()
                .claim("id", 1L)
                .claim("userid", "testuser")
                .claim("name", "Test User")
                .claim("role", "USER")
                .setIssuer("kta-portal-school") // Wrong issuer
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
        
        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenWithWrongIssuer);
        
        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
        
        verify(filterChain, times(1)).doFilter(request, response);
    }
    
    @Test
    void testDoFilterInternal_WithNoIssuer_DoesNotSetAuthentication() throws Exception {
        // Given - Create token without issuer
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        String tokenWithoutIssuer = Jwts.builder()
                .claim("id", 1L)
                .claim("userid", "testuser")
                .claim("name", "Test User")
                .claim("role", "USER")
                // No issuer
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
        
        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenWithoutIssuer);
        
        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
        
        verify(filterChain, times(1)).doFilter(request, response);
    }
    
    @Test
    void testDoFilterInternal_WithNoToken_DoesNotSetAuthentication() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn(null);
        
        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
        
        verify(filterChain, times(1)).doFilter(request, response);
    }
    
    @Test
    void testDoFilterInternal_WithInvalidToken_DoesNotSetAuthentication() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidtoken");
        
        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
        
        verify(filterChain, times(1)).doFilter(request, response);
    }
    
    @Test
    void testDoFilterInternal_WithExpiredToken_DoesNotSetAuthentication() throws Exception {
        // Given - Create expired token
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        String expiredToken = Jwts.builder()
                .claim("id", 1L)
                .claim("userid", "testuser")
                .claim("name", "Test User")
                .claim("role", "USER")
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis() - 172800000)) // 2 days ago
                .setExpiration(new Date(System.currentTimeMillis() - 86400000)) // 1 day ago
                .signWith(key)
                .compact();
        
        when(request.getHeader("Authorization")).thenReturn("Bearer " + expiredToken);
        
        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
        
        verify(filterChain, times(1)).doFilter(request, response);
    }
    
    @Test
    void testDoFilterInternal_WithMultipleDifferentServiceTokens_AllRejected() throws Exception {
        // Only include non-admin service issuers (admin tokens should be accepted)
        String[] invalidServiceIssuers = {
            "kta-portal-school",
            "kta-portal-front", 
            "kta-portal-api"
        };
        
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        
        for (String serviceIssuer : invalidServiceIssuers) {
            // Setup
            SecurityContextHolder.clearContext();
            
            // Create token with different service issuer
            String token = Jwts.builder()
                    .claim("id", 1L)
                    .claim("userid", "testuser")
                    .claim("name", "Test User")
                    .claim("role", "USER")
                    .setIssuer(serviceIssuer)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .signWith(key)
                    .compact();
            
            when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
            
            // When
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
            
            // Then
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assertNull(authentication, "Token with issuer " + serviceIssuer + " should not set authentication");
        }
    }
}