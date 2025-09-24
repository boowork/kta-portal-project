package com.kta.portal.admin.feature.api.user;

import com.kta.portal.admin.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class GetUsersControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllUsers_WithoutAuthentication_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetAllUsers_WithUserRole_ShouldReturn200() throws Exception {
        mockMvc.perform(withUserAuth(get("/api/users")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testGetAllUsers_WithAdminRole_ShouldReturn200() throws Exception {
        mockMvc.perform(withAdminAuth(get("/api/users")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }
}