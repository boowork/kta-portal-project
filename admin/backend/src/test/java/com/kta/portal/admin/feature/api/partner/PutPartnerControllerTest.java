package com.kta.portal.admin.feature.api.partner;

import com.kta.portal.admin.BaseIntegrationTest;
import com.kta.portal.admin.feature.repository.PartnerRepository;
import com.kta.portal.admin.feature.repository.model.Partner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class PutPartnerControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    void testUpdatePartner_WithoutAuthentication_ShouldReturn401() throws Exception {
        String existingPartnerId = "01999880-0000-7000-8000-000000000001";
        String requestBody = """
            {
                "partnerName": "Updated Partner",
                "isActive": false
            }
            """;

        mockMvc.perform(put("/api/partners/" + existingPartnerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testUpdatePartner_WithAuth_WithValidId_ShouldReturn200() throws Exception {
        // Create a partner first
        Partner partner = new Partner();
        partner.setPartnerName("Test Partner");
        partner.setIsActive(true);
        partner.setCreatedAt(java.time.LocalDateTime.now());
        Partner savedPartner = partnerRepository.save(partner);
        
        String requestBody = """
            {
                "partnerName": "Updated Test Partner",
                "isActive": false
            }
            """;

        mockMvc.perform(withAuth(put("/api/partners/" + savedPartner.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.partnerName").value("Updated Test Partner"))
                .andExpect(jsonPath("$.data.isActive").value(false));
    }

    @Test
    void testUpdatePartner_WithAuth_WithInvalidId_ShouldReturn404() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        String requestBody = """
            {
                "partnerName": "Updated Partner"
            }
            """;

        mockMvc.perform(withAuth(put("/api/partners/" + nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0].message").value("Partner not found"));
    }

    @Test
    void testUpdatePartner_WithAuth_PartialUpdate_ShouldReturn200() throws Exception {
        // Create a partner first
        Partner partner = new Partner();
        partner.setPartnerName("Test Partner");
        partner.setIsActive(true);
        partner.setCreatedAt(java.time.LocalDateTime.now());
        Partner savedPartner = partnerRepository.save(partner);
        
        String requestBody = """
            {
                "isActive": false
            }
            """;

        mockMvc.perform(withAuth(put("/api/partners/" + savedPartner.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.isActive").value(false));
    }
}