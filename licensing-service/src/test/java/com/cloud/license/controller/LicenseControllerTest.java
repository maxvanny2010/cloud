package com.cloud.license.controller;

import com.cloud.license.model.License;
import com.cloud.license.service.LicenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * LicenseControllerTest.
 *
 * @author legion
 * @version 7.0
 * @since 7.05.2026
 */
@WebMvcTest(LicenseController.class)
@ActiveProfiles("test")
class LicenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LicenseService licenseService;

    private static final String VALID_ORG_ID = "e6a625cc-718b-48c2-ac76-1dfdff9a531e";
    private static final String VALID_LICENSE_ID = "f2a9c9d4-d2c0-44fa-97fe-724d77173c62";

    private License buildLicense() {
        return License.builder()
                .licenseId(VALID_LICENSE_ID)
                .organizationId(VALID_ORG_ID)
                .productName("Cloud Infrastructure Suite")
                .licenseType("complete")
                .comment("test")
                .build();
    }

    @Test
    @DisplayName("GET /{licenseId}: returns 200 with license JSON")
    void getLicense_validRequest_returns200() throws Exception {
        when(licenseService.getLicense(VALID_LICENSE_ID, VALID_ORG_ID, ""))
                .thenReturn(buildLicense());

        mockMvc.perform(get("/v1/organization/{orgId}/license/{licId}",
                        VALID_ORG_ID, VALID_LICENSE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseId").value(VALID_LICENSE_ID))
                .andExpect(jsonPath("$.productName").value("Cloud Infrastructure Suite"))
                .andExpect(jsonPath("$.licenseType").value("complete"));
    }

    @Test
    @DisplayName("GET /{licenseId}: returns 400 when organizationId has invalid UUID format")
    void getLicense_invalidOrgUUID_returns400() throws Exception {
        mockMvc.perform(get("/v1/organization/{orgId}/license/{licId}",
                        "not-a-uuid", VALID_LICENSE_ID))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(licenseService);
    }

    @Test
    @DisplayName("GET /: returns list of licenses for organization")
    void getLicenses_returnsListWithOk() throws Exception {
        when(licenseService.getLicensesByOrganization(VALID_ORG_ID))
                .thenReturn(List.of(buildLicense()));

        mockMvc.perform(get("/v1/organization/{orgId}/license/", VALID_ORG_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].licenseId").value(VALID_LICENSE_ID));
    }


    @Test
    @DisplayName("POST /: creates license, returns 200 with license in body")
    void createLicense_returns200WithBody() throws Exception {
        License input = buildLicense();
        when(licenseService.createLicense(any(License.class))).thenReturn(input);

        mockMvc.perform(post("/v1/organization/{orgId}/license", VALID_ORG_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Cloud Infrastructure Suite"));

        verify(licenseService).createLicense(any(License.class));
    }


    @Test
    @DisplayName("PUT /: updates license, returns 200")
    void updateLicense_returns200() throws Exception {
        License input = buildLicense();
        when(licenseService.updateLicense(any(License.class))).thenReturn(input);

        mockMvc.perform(put("/v1/organization/{orgId}/license", VALID_ORG_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseId").value(VALID_LICENSE_ID));
    }

    @Test
    @DisplayName("DELETE /{licenseId}: returns 200 with confirmation message")
    void deleteLicense_returns200WithMessage() throws Exception {
        when(licenseService.deleteLicense(VALID_LICENSE_ID))
                .thenReturn("License " + VALID_LICENSE_ID + " deleted");

        mockMvc.perform(delete("/v1/organization/{orgId}/license/{licId}",
                        VALID_ORG_ID, VALID_LICENSE_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("License " + VALID_LICENSE_ID + " deleted"));
    }
}