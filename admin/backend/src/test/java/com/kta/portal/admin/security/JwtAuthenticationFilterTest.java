package com.kta.portal.admin.security;

import com.kta.portal.admin.support.TestUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JwtTokenProvider jwtTokenProvider;
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;
    
    @Mock
    private FilterChain filterChain;
    
    @Autowired
    private Environment environment;
    
    private final String secretKey = "ThisIsAVerySecureSecretKeyForJWTTokenGenerationAndValidation2025";
    private final String issuer = "kta-portal-admin";
    
    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtTokenProvider, "accessTokenValidity", 86400000L);
        ReflectionTestUtils.setField(jwtTokenProvider, "issuer", issuer);
        
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider, environment);
        SecurityContextHolder.clearContext();
        
        // Reset mocks before each test (but don't reset environment in setUp)
        reset(request, response, filterChain);
    }
    
    
    @Test
    void testDoFilterInternal_WithValidToken_SetsAuthentication() throws Exception {
        // Given
        String token = jwtTokenProvider.generateToken(UUID.randomUUID(), "testuser", "Test User");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(request.getHeader("DEV_AUTH")).thenReturn(null); // No DEV_AUTH header
        
        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals("testuser", authentication.getName());
        assertTrue(authentication.getAuthorities().isEmpty());
        
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
                .setIssuer("kta-portal-school") // Wrong issuer
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
        
        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenWithWrongIssuer);
        when(request.getHeader("DEV_AUTH")).thenReturn(null);
        
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
                // No issuer
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
        
        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenWithoutIssuer);
        when(request.getHeader("DEV_AUTH")).thenReturn(null);
        
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
        when(request.getHeader("DEV_AUTH")).thenReturn(null);
        
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
        when(request.getHeader("DEV_AUTH")).thenReturn(null);
        
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
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis() - 172800000)) // 2 days ago
                .setExpiration(new Date(System.currentTimeMillis() - 86400000)) // 1 day ago
                .signWith(key)
                .compact();
        
        when(request.getHeader("Authorization")).thenReturn("Bearer " + expiredToken);
        when(request.getHeader("DEV_AUTH")).thenReturn(null);
        
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
                    .setIssuer(serviceIssuer)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .signWith(key)
                    .compact();
            
            when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(request.getHeader("DEV_AUTH")).thenReturn(null);
            
            // When
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
            
            // Then
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assertNull(authentication, "Token with issuer " + serviceIssuer + " should not set authentication");
        }
    }
    
    @Test
    void testDoFilterInternal_WithDevAuthHeader_SetsAuthentication() throws Exception {
        // Given
        when(request.getHeader("DEV_AUTH")).thenReturn(TestUtils.buildDevAuthHeader());
        
        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(TestUtils.DEFAULT_USER_ID_STR, authentication.getName());
        assertTrue(authentication.getAuthorities().isEmpty());
        
        verify(filterChain, times(1)).doFilter(request, response);
    }
    
    @Test
    void testDoFilterInternal_WithInvalidDevAuthHeader_DoesNotSetAuthentication() throws Exception {
        // Given - Invalid DEV_AUTH format (missing parts)
        when(request.getHeader("DEV_AUTH")).thenReturn("invalid:format");
        
        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
        
        verify(filterChain, times(1)).doFilter(request, response);
    }
    
    @Test
    void testDoFilterInternal_WithDevAuthInTestProfile_SetsAuthentication() throws Exception {
        // Given - Test profile is active (DEV_AUTH should work)
        when(request.getHeader("DEV_AUTH")).thenReturn(TestUtils.buildDevAuthHeader());
        
        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(TestUtils.DEFAULT_USER_ID_STR, authentication.getName());
        
        verify(filterChain, times(1)).doFilter(request, response);
    }
    
}