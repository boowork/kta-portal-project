package com.kta.portal.admin.feature.api.user;

import com.kta.portal.admin.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class GetUserControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void initInsert() throws SQLException {
        insertTestData("""
            INSERT INTO portal_users (id, userid, password, name, created_at, updated_at)
            VALUES 
            ('0199987d-8798-7a79-be6d-c1aa9449d8aa'::uuid, 'admin', '{noop}admin', '관리자', now(), now()),
            ('0199987e-0fa0-748d-af0f-37970e02e326'::uuid, 'user', '{noop}user', '사용자', now(), now());
            """);
    }

    @Test
    void testGetUserById_WithoutAuthentication_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/users/0199987d-8798-7a79-be6d-c1aa9449d8aa"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetUserById_WithUserRole() throws Exception {
        mockMvc.perform(withUserAuth(get("/api/users/0199987d-8798-7a79-be6d-c1aa9449d8aa")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value("0199987d-8798-7a79-be6d-c1aa9449d8aa"))
                .andExpect(jsonPath("$.data.userid").value("admin"));
    }

    @Test
    void testGetUserById_WithAdminRole() throws Exception {
        mockMvc.perform(withAdminAuth(get("/api/users/0199987e-0fa0-748d-af0f-37970e02e326")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value("0199987e-0fa0-748d-af0f-37970e02e326"))
                .andExpect(jsonPath("$.data.userid").value("user"));
    }

    @Test
    void testGetUserByIdNotFound_WithUserRole() throws Exception {
        mockMvc.perform(withUserAuth(get("/api/users/01999999-9999-7999-8999-999999999999")))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0].code").value("NOT_FOUND"));
    }
}