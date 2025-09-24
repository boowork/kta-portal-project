package com.kta.portal.admin.feature.api.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kta.portal.admin.BaseIntegrationTest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.crypto.SecretKey;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class PostLoginControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Test
    void testLoginSuccess_WithAdminCredentials() throws Exception {
        String loginJson = """
                {
                    "userid": "admin",
                    "password": "admin"
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.userid").value("admin"))
                .andExpect(jsonPath("$.data.name").value("관리자"))
                .andExpect(jsonPath("$.data.role").value("ADMIN"))
                .andReturn();
        
        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> response = objectMapper.readValue(responseBody, new TypeReference<>() {});
        @SuppressWarnings("unchecked")
        Map<String, String> data = (Map<String, String>) response.get("data");
        String token = data.get("accessToken");
        
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testLoginSuccess_WithUserCredentials() throws Exception {
        String loginJson = """
                {
                    "userid": "user",
                    "password": "user"
                }
                """;

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.userid").value("user"))
                .andExpect(jsonPath("$.data.name").value("사용자"))
                .andExpect(jsonPath("$.data.role").value("USER"));
    }

    @Test
    void testLoginFailure_InvalidPassword() throws Exception {
        String loginJson = """
                {
                    "userid": "admin",
                    "password": "wrongpassword"
                }
                """;

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").value("password"))
                .andExpect(jsonPath("$.errors[0].message").value("Invalid credentials"))
                .andExpect(jsonPath("$.errors[0].code").value("INVALID_CREDENTIALS"));
    }

    @Test
    void testLoginFailure_UserNotFound() throws Exception {
        String loginJson = """
                {
                    "userid": "nonexistent",
                    "password": "password"
                }
                """;

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").value("userid"))
                .andExpect(jsonPath("$.errors[0].message").value("Invalid credentials"))
                .andExpect(jsonPath("$.errors[0].code").value("INVALID_CREDENTIALS"));
    }

    @Test
    void testJwtTokenValidation_WithCorrectIssuer() throws Exception {
        String loginJson = """
                {
                    "userid": "admin",
                    "password": "admin"
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> response = objectMapper.readValue(responseBody, new TypeReference<>() {});
        @SuppressWarnings("unchecked")
        Map<String, String> data = (Map<String, String>) response.get("data");
        String token = data.get("accessToken");

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertEquals("admin", claims.get("userid"));
        assertEquals("관리자", claims.get("name"));
        assertEquals("ADMIN", claims.get("role"));
        assertEquals("kta-portal-admin", claims.getIssuer());
        assertNotNull(claims.get("id"));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }
}