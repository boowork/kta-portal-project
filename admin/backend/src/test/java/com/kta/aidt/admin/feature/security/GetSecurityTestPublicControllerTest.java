package com.kta.aidt.admin.feature.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GetSecurityTestPublicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPublicEndpoint_ShouldBeAccessible_WithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/security-test/public"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.message").value("This is a public endpoint"));
    }
}