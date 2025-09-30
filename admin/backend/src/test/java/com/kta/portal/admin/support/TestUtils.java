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
    
    // Test user data
    public static final UUID ADMIN_USER_ID = UUID.fromString("0199987d-8798-7a79-be6d-c1aa9449d8aa");
    public static final String ADMIN_USER_ID_STR = "admin";
    public static final String ADMIN_USER_NAME = "관리자";
    
    public static final UUID REGULAR_USER_ID = UUID.fromString("0199987e-0fa0-748d-af0f-37970e02e326");
    public static final String REGULAR_USER_ID_STR = "user";
    public static final String REGULAR_USER_NAME = "사용자";
    
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
     * Generate admin JWT token
     */
    public static String generateAdminToken() {
        return generateTestToken(ADMIN_USER_ID, ADMIN_USER_ID_STR, ADMIN_USER_NAME);
    }
    
    /**
     * Generate regular user JWT token
     */
    public static String generateUserToken() {
        return generateTestToken(REGULAR_USER_ID, REGULAR_USER_ID_STR, REGULAR_USER_NAME);
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
     * Add admin JWT authorization to request
     */
    public static MockHttpServletRequestBuilder withAdminJwtAuth(MockHttpServletRequestBuilder builder) {
        return withJwtAuth(builder, generateAdminToken());
    }
    
    /**
     * Add user JWT authorization to request
     */
    public static MockHttpServletRequestBuilder withUserJwtAuth(MockHttpServletRequestBuilder builder) {
        return withJwtAuth(builder, generateUserToken());
    }
    
    /**
     * Add DEV_AUTH header to request
     */
    public static MockHttpServletRequestBuilder withDevAuth(MockHttpServletRequestBuilder builder, 
                                                           UUID id, String userid, String name) {
        return builder.header("DEV_AUTH", id + ":" + userid + ":" + name);
    }
    
    /**
     * Add admin DEV_AUTH header to request
     */
    public static MockHttpServletRequestBuilder withAdminDevAuth(MockHttpServletRequestBuilder builder) {
        return withDevAuth(builder, ADMIN_USER_ID, ADMIN_USER_ID_STR, ADMIN_USER_NAME);
    }
    
    /**
     * Add user DEV_AUTH header to request
     */
    public static MockHttpServletRequestBuilder withUserDevAuth(MockHttpServletRequestBuilder builder) {
        return withDevAuth(builder, REGULAR_USER_ID, REGULAR_USER_ID_STR, REGULAR_USER_NAME);
    }
    
    // DEV_AUTH string builders
    
    /**
     * Build DEV_AUTH header string
     */
    public static String buildDevAuthHeader(UUID id, String userid, String name) {
        return id + ":" + userid + ":" + name;
    }
    
    /**
     * Build admin DEV_AUTH header string
     */
    public static String buildAdminDevAuthHeader() {
        return buildDevAuthHeader(ADMIN_USER_ID, ADMIN_USER_ID_STR, ADMIN_USER_NAME);
    }
    
    /**
     * Build user DEV_AUTH header string
     */
    public static String buildUserDevAuthHeader() {
        return buildDevAuthHeader(REGULAR_USER_ID, REGULAR_USER_ID_STR, REGULAR_USER_NAME);
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
     * Build admin login JSON
     */
    public static String buildAdminLoginJson() {
        return buildLoginJson(ADMIN_USER_ID_STR, "admin");
    }
    
    /**
     * Build user login JSON
     */
    public static String buildUserLoginJson() {
        return buildLoginJson(REGULAR_USER_ID_STR, "user");
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