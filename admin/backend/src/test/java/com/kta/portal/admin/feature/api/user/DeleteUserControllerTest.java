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
            INSERT INTO portal_users (id, userid, password, name, created_at, updated_at)
            VALUES ('01999999-0000-7000-8000-000000000001'::uuid, 'test', '{noop}password', '테스트', now(), now());
            """);
    }

    @Test
    void testDeleteUser_WithoutAuthentication_ShouldReturn401() throws Exception {
        mockMvc.perform(delete("/api/users/01999999-0000-7000-8000-000000000001"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testDeleteUser_WithUserRole() throws Exception {
        mockMvc.perform(withUserAuth(delete("/api/users/01999999-0000-7000-8000-000000000001")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testDeleteUserNotFound_WithUserRole() throws Exception {
        mockMvc.perform(withUserAuth(delete("/api/users/01999999-9999-7999-8999-999999999999")))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0].code").value("NOT_FOUND"));
    }
}