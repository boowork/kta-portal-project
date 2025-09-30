package com.kta.portal.admin.feature.api.partner;

import com.kta.portal.admin.BaseIntegrationTest;
import com.kta.portal.admin.feature.repository.PartnerRepository;
import com.kta.portal.admin.feature.repository.model.Partner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class DeletePartnerControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    void testDeletePartner_WithoutAuthentication_ShouldReturn401() throws Exception {
        String existingPartnerId = "01999880-0000-7000-8000-000000000001";
        
        mockMvc.perform(delete("/api/partners/" + existingPartnerId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testDeletePartner_WithAuth_WithValidId_ShouldReturn200() throws Exception {
        // Create a partner to delete
        Partner partner = new Partner();
        partner.setPartnerName("Partner to Delete");
        partner.setIsActive(true);
        partner.setCreatedAt(java.time.LocalDateTime.now());
        Partner savedPartner = partnerRepository.save(partner);
        
        // Now delete the partner
        mockMvc.perform(withAuth(delete("/api/partners/" + savedPartner.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeletePartner_WithAuth_WithInvalidId_ShouldReturn404() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        
        mockMvc.perform(withAuth(delete("/api/partners/" + nonExistentId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0].message").value("Partner not found"));
    }
}