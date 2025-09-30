package com.kta.portal.admin.feature.api.partner;

import com.kta.portal.admin.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class PostPartnerControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreatePartner_WithoutAuthentication_ShouldReturn401() throws Exception {
        String requestBody = """
            {
                "partnerName": "New Partner",
                "isActive": true
            }
            """;

        mockMvc.perform(post("/api/partners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testCreatePartner_WithAuth_ShouldReturn201() throws Exception {
        String requestBody = """
            {
                "partnerName": "New Test Partner",
                "isActive": true
            }
            """;

        mockMvc.perform(withAuth(post("/api/partners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.partnerName").value("New Test Partner"))
                .andExpect(jsonPath("$.data.isActive").value(true));
    }

}