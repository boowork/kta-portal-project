package com.kta.aidt.admin.feature.user;

import com.kta.aidt.admin.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class PostUserControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateUser_WithoutAuthentication_ShouldReturn401() throws Exception {
        String createUserJson = """
                {
                    "userid": "testuser",
                    "password": "password123",
                    "name": "Test User",
                    "role": "USER"
                }
                """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testCreateUser_WithUserRole() throws Exception {
        String createUserJson = """
                {
                    "userid": "testuser1",
                    "password": "password123",
                    "name": "Test User 1",
                    "role": "USER"
                }
                """;

        mockMvc.perform(withUserAuth(post("/api/v1/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.userid").value("testuser1"));
    }

    @Test
    void testCreateUser_WithAdminRole() throws Exception {
        String createUserJson = """
                {
                    "userid": "testuser2",
                    "password": "password123",
                    "name": "Test User 2",
                    "role": "ADMIN"
                }
                """;

        mockMvc.perform(withAdminAuth(post("/api/v1/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.userid").value("testuser2"));
    }
}