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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class GetPartnerControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    void testGetPartnerById_WithoutAuthentication_ShouldReturn401() throws Exception {
        UUID partnerId = UUID.randomUUID();
        mockMvc.perform(get("/api/partners/" + partnerId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetPartnerById_WithAuth_WithValidId_ShouldReturn200() throws Exception {
        // Create a partner for testing
        Partner partner = new Partner();
        partner.setPartnerName("Test Partner");
        partner.setIsActive(true);
        partner.setCreatedAt(java.time.LocalDateTime.now());
        Partner savedPartner = partnerRepository.save(partner);
        
        mockMvc.perform(withAuth(get("/api/partners/" + savedPartner.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.partnerName").value("Test Partner"))
                .andExpect(jsonPath("$.data.isActive").value(true));
    }

    @Test
    void testGetPartnerById_WithAuth_WithInvalidId_ShouldReturn404() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        
        mockMvc.perform(withAuth(get("/api/partners/" + nonExistentId)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0].message").value("Partner not found"));
    }
}