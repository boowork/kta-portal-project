package com.kta.portal.admin.feature.api.user;

import com.kta.portal.admin.BaseIntegrationTest;
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
                    "name": "Updated Name"
                }
                """;

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testUpdateUser_WithUserRole() throws Exception {
        String updateUserJson = """
                {
                    "name": "Updated User Name"
                }
                """;

        mockMvc.perform(withUserAuth(put("/api/users/1"))
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
                    "name": "Updated Admin Name"
                }
                """;

        mockMvc.perform(withAdminAuth(put("/api/users/2"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Updated Admin Name"));
    }
}