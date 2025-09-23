package com.kta.aidt.admin.feature.user;

import com.kta.aidt.admin.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class PutUserControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUpdateUser_WithoutAuthentication_ShouldReturn401() throws Exception {
        String updateUserJson = """
                {
                    "name": "Updated Name",
                    "role": "ADMIN"
                }
                """;

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testUpdateUser_WithUserRole() throws Exception {
        String updateUserJson = """
                {
                    "name": "Updated User Name",
                    "role": "USER"
                }
                """;

        mockMvc.perform(withUserAuth(put("/api/v1/users/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Updated User Name"));
    }

    @Test
    void testUpdateUser_WithAdminRole() throws Exception {
        String updateUserJson = """
                {
                    "name": "Updated Admin Name",
                    "role": "ADMIN"
                }
                """;

        mockMvc.perform(withAdminAuth(put("/api/v1/users/2"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Updated Admin Name"));
    }
}