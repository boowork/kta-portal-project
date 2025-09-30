package com.kta.portal.admin.support;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

/**
 * Comprehensive test utilities for admin backend testing
 */
public class TestUtils {
    
    // Common test constants
    public static final String TEST_SECRET_KEY = "ThisIsAVerySecureSecretKeyForJWTTokenGenerationAndValidation2025";
    public static final String TEST_ISSUER = "kta-portal-admin";
    public static final long TEST_TOKEN_VALIDITY = 86400000L; // 24 hours
    
    // Default test user data (no role distinction)
    public static final UUID DEFAULT_USER_ID = UUID.fromString("0199987e-0fa0-748d-af0f-37970e02e326");
    public static final String DEFAULT_USER_ID_STR = "user";
    public static final String DEFAULT_USER_NAME = "사용자";
    
    private TestUtils() {
        // Utility class
    }
    
    /**
     * Generate JWT token for testing
     */
    public static String generateTestToken(UUID id, String userid, String name) {
        return generateTestToken(id, userid, name, TEST_ISSUER, TEST_TOKEN_VALIDITY);
    }
    
    /**
     * Generate JWT token with custom parameters
     */
    public static String generateTestToken(UUID id, String userid, String name, String issuer, long validity) {
        SecretKey key = Keys.hmacShaKeyFor(TEST_SECRET_KEY.getBytes());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validity);

        return Jwts.builder()
                .claim("id", id.toString())
                .claim("userid", userid)
                .claim("name", name)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }
    
    /**
     * Generate default JWT token (no role distinction)
     */
    public static String generateToken() {
        return generateTestToken(DEFAULT_USER_ID, DEFAULT_USER_ID_STR, DEFAULT_USER_NAME);
    }
    
    /**
     * @deprecated Use generateToken() instead
     */
    @Deprecated
    public static String generateAdminToken() {
        return generateToken();
    }
    
    /**
     * @deprecated Use generateToken() instead
     */
    @Deprecated
    public static String generateUserToken() {
        return generateToken();
    }
    
    /**
     * Generate expired token for testing
     */
    public static String generateExpiredToken(UUID id, String userid, String name) {
        return generateTestToken(id, userid, name, TEST_ISSUER, -1000L);
    }
    
    /**
     * Generate token with wrong issuer
     */
    public static String generateWrongIssuerToken(UUID id, String userid, String name, String wrongIssuer) {
        return generateTestToken(id, userid, name, wrongIssuer, TEST_TOKEN_VALIDITY);
    }
    
    // Request builder helpers
    
    /**
     * Add JWT Authorization header to request
     */
    public static MockHttpServletRequestBuilder withJwtAuth(MockHttpServletRequestBuilder builder, String token) {
        return builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
    
    /**
     * Add JWT authorization to request (unified auth)
     */
    public static MockHttpServletRequestBuilder withJwtAuth(MockHttpServletRequestBuilder builder) {
        return withJwtAuth(builder, generateToken());
    }
    
    /**
     * @deprecated Use withJwtAuth(builder) instead
     */
    @Deprecated
    public static MockHttpServletRequestBuilder withAdminJwtAuth(MockHttpServletRequestBuilder builder) {
        return withJwtAuth(builder);
    }
    
    /**
     * @deprecated Use withJwtAuth(builder) instead
     */
    @Deprecated
    public static MockHttpServletRequestBuilder withUserJwtAuth(MockHttpServletRequestBuilder builder) {
        return withJwtAuth(builder);
    }
    
    /**
     * Add DEV_AUTH header to request
     */
    public static MockHttpServletRequestBuilder withDevAuth(MockHttpServletRequestBuilder builder, 
                                                           UUID id, String userid, String name) {
        return builder.header("DEV_AUTH", id + ":" + userid + ":" + name);
    }
    
    /**
     * Add DEV_AUTH header to request (unified auth)
     */
    public static MockHttpServletRequestBuilder withDevAuth(MockHttpServletRequestBuilder builder) {
        return withDevAuth(builder, DEFAULT_USER_ID, DEFAULT_USER_ID_STR, DEFAULT_USER_NAME);
    }
    
    // DEV_AUTH string builders
    
    /**
     * Build DEV_AUTH header string
     */
    public static String buildDevAuthHeader(UUID id, String userid, String name) {
        return id + ":" + userid + ":" + name;
    }
    
    /**
     * Build default DEV_AUTH header string
     */
    public static String buildDevAuthHeader() {
        return buildDevAuthHeader(DEFAULT_USER_ID, DEFAULT_USER_ID_STR, DEFAULT_USER_NAME);
    }
    
    // JSON builders for common test scenarios
    
    /**
     * Build login JSON
     */
    public static String buildLoginJson(String userid, String password) {
        return String.format("""
                {
                    "userid": "%s",
                    "password": "%s"
                }
                """, userid, password);
    }
    
    /**
     * Build default login JSON
     */
    public static String buildDefaultLoginJson() {
        return buildLoginJson(DEFAULT_USER_ID_STR, "user");
    }
    
    /**
     * Build create user JSON
     */
    public static String buildCreateUserJson(String userid, String password, String name) {
        return String.format("""
                {
                    "userid": "%s",
                    "password": "%s",
                    "name": "%s"
                }
                """, userid, password, name);
    }
    
    /**
     * Build update user JSON
     */
    public static String buildUpdateUserJson(String name, String password) {
        if (password != null) {
            return String.format("""
                    {
                        "name": "%s",
                        "password": "%s"
                    }
                    """, name, password);
        } else {
            return String.format("""
                    {
                        "name": "%s"
                    }
                    """, name);
        }
    }
    
    /**
     * Build refresh token JSON
     */
    public static String buildRefreshTokenJson(String refreshToken) {
        return String.format("""
                {
                    "refreshToken": "%s"
                }
                """, refreshToken);
    }
}