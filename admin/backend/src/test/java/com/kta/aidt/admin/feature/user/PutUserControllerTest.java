package com.kta.aidt.admin.feature.user;

import com.kta.aidt.admin.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class PutUserControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void testUpdateUser() throws Exception {
        // First create a user
        String createUserJson = """
                {
                    "userid": "updateuser789",
                    "password": "password123",
                    "name": "Original Name",
                    "role": "USER"
                }
                """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserJson))
                .andExpect(status().isCreated());

        // Then update the user
        String updateUserJson = """
                {
                    "name": "Updated Name",
                    "role": "ADMIN"
                }
                """;

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.userid").value("updateuser789"))
                .andExpect(jsonPath("$.data.name").value("Updated Name"))
                .andExpect(jsonPath("$.data.role").value("ADMIN"))
                .andExpect(jsonPath("$.data.password").doesNotExist())
                .andExpect(jsonPath("$.data.updatedAt").exists())
                .andExpect(jsonPath("$.errors").doesNotExist());
    }

    @Test
    @WithMockUser
    void testUpdateUserNotFound() throws Exception {
        String updateUserJson = """
                {
                    "name": "Updated Name",
                    "role": "ADMIN"
                }
                """;

        mockMvc.perform(put("/api/v1/users/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").value("User not found"))
                .andExpect(jsonPath("$.errors[0].code").value("NOT_FOUND"));
    }
}