package com.kta.portal.admin.feature.api.user;

import com.kta.portal.admin.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class DeleteUserControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void initInsert() throws SQLException {
        insertTestData("""
            INSERT INTO users (userid, password, name, role, created_at, updated_at)
            VALUES ('test', '{nohup}password', '테스트', 'USER', now(), now());
            """);
    }

    @Test
    void testDeleteUser_WithoutAuthentication_ShouldReturn401() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testDeleteUser_WithUserRole() throws Exception {
        mockMvc.perform(withUserAuth(delete("/api/users/1")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testDeleteUser_WithAdminRole() throws Exception {
        mockMvc.perform(withAdminAuth(delete("/api/users/1")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testDeleteUserNotFound_WithUserRole() throws Exception {
        mockMvc.perform(withUserAuth(delete("/api/users/999")))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0].code").value("NOT_FOUND"));
    }
}