package com.cloud.license.service;

import com.cloud.license.config.ServiceConfig;
import com.cloud.license.model.License;
import com.cloud.license.model.Organization;
import com.cloud.license.repository.LicenseRepository;
import com.cloud.license.service.client.OrganizationDiscoveryClient;
import com.cloud.license.service.client.OrganizationFeignClient;
import com.cloud.license.service.client.OrganizationRestTemplateClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * LicenseServiceTest.
 *
 * @author legion
 * @version 7.0
 * @since 7.05.2026
 */
@ExtendWith(MockitoExtension.class)
class LicenseServiceTest {

    @Mock
    private LicenseRepository licenseRepository;

    @Mock
    private OrganizationFeignClient organizationFeignClient;

    @Mock
    private OrganizationRestTemplateClient organizationRestClient;

    @Mock
    private OrganizationDiscoveryClient organizationDiscoveryClient;

    @Mock
    private ServiceConfig config;

    @Mock
    private MessageSource messages;

    @InjectMocks
    private LicenseService licenseService;

    private static final String ORG_ID = "e6a625cc-718b-48c2-ac76-1dfdff9a531e";
    private static final String LICENSE_ID = "f2a9c9d4-d2c0-44fa-97fe-724d77173c62";
    private static final String COMMENT = "test-property";

    private License sampleLicense;
    private Organization sampleOrganization;

    @BeforeEach
    void setUp() {
        sampleLicense = License.builder()
                .licenseId(LICENSE_ID)
                .organizationId(ORG_ID)
                .productName("Cloud Infrastructure Suite")
                .licenseType("complete")
                .build();

        sampleOrganization = new Organization();
        sampleOrganization.setId(ORG_ID);
        sampleOrganization.setName("ATU Donegal");
        sampleOrganization.setContactName("Head of Faculty");
        sampleOrganization.setContactEmail("computing@atu.ie");
        sampleOrganization.setContactPhone("+353749186000");
    }

    @Test
    @DisplayName("getLicense: throws IllegalArgumentException when license not found")
    void getLicense_notFound_throwsException() {
        when(licenseRepository.findByOrganizationIdAndLicenseId(ORG_ID, LICENSE_ID))
                .thenReturn(null);
        when(messages.getMessage(eq("license.search.error.message"), any(), any(Locale.class)))
                .thenReturn("License not found");

        assertThatThrownBy(() -> licenseService.getLicense(LICENSE_ID, ORG_ID, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("License not found");
    }

    @Test
    @DisplayName("getLicense: returns license enriched with organization data via feign")
    void getLicense_withFeign_enrichesOrganizationFields() {
        when(config.getProperty()).thenReturn(COMMENT);
        when(licenseRepository.findByOrganizationIdAndLicenseId(ORG_ID, LICENSE_ID))
                .thenReturn(sampleLicense);
        when(organizationFeignClient.getOrganization(ORG_ID))
                .thenReturn(sampleOrganization);

        License result = licenseService.getLicense(LICENSE_ID, ORG_ID, "feign");

        assertThat(result.getOrganizationName()).isEqualTo("ATU Donegal");
        assertThat(result.getContactEmail()).isEqualTo("computing@atu.ie");
        assertThat(result.getComment()).isEqualTo(COMMENT);

        verify(organizationFeignClient).getOrganization(ORG_ID);
        verifyNoInteractions(organizationRestClient, organizationDiscoveryClient);
    }

    @Test
    @DisplayName("getLicense: uses rest client when clientType=rest")
    void getLicense_withRest_usesRestTemplateClient() {
        when(licenseRepository.findByOrganizationIdAndLicenseId(ORG_ID, LICENSE_ID))
                .thenReturn(sampleLicense);
        when(organizationRestClient.getOrganization(ORG_ID))
                .thenReturn(sampleOrganization);

        licenseService.getLicense(LICENSE_ID, ORG_ID, "rest");

        verify(organizationRestClient).getOrganization(ORG_ID);
        verifyNoInteractions(organizationFeignClient, organizationDiscoveryClient);
    }

    @Test
    @DisplayName("getLicense: uses discovery client when clientType=discovery")
    void getLicense_withDiscovery_usesDiscoveryClient() {
        when(licenseRepository.findByOrganizationIdAndLicenseId(ORG_ID, LICENSE_ID))
                .thenReturn(sampleLicense);
        when(organizationDiscoveryClient.getOrganization(ORG_ID))
                .thenReturn(sampleOrganization);

        licenseService.getLicense(LICENSE_ID, ORG_ID, "discovery");

        verify(organizationDiscoveryClient).getOrganization(ORG_ID);
        verifyNoInteractions(organizationFeignClient, organizationRestClient);
    }

    @Test
    @DisplayName("getLicense: uses rest client as default when clientType is unknown")
    void getLicense_unknownClientType_defaultsToRest() {
        when(licenseRepository.findByOrganizationIdAndLicenseId(ORG_ID, LICENSE_ID))
                .thenReturn(sampleLicense);
        when(organizationRestClient.getOrganization(ORG_ID))
                .thenReturn(null);

        License result = licenseService.getLicense(LICENSE_ID, ORG_ID, "unknown");

        verify(organizationRestClient).getOrganization(ORG_ID);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("getLicense: organization null does not break enrichment")
    void getLicense_organizationNull_returnsLicenseWithoutOrgData() {
        when(config.getProperty()).thenReturn(COMMENT);
        when(licenseRepository.findByOrganizationIdAndLicenseId(ORG_ID, LICENSE_ID))
                .thenReturn(sampleLicense);
        when(organizationRestClient.getOrganization(ORG_ID))
                .thenReturn(null);

        License result = licenseService.getLicense(LICENSE_ID, ORG_ID, "");

        assertThat(result.getOrganizationName()).isNull();
        assertThat(result.getComment()).isEqualTo(COMMENT);
    }

    @Test
    @DisplayName("createLicense: sets UUID and saves, returns license with comment")
    void createLicense_setsIdAndSaves() {
        when(config.getProperty()).thenReturn(COMMENT);
        License input = License.builder()
                .organizationId(ORG_ID)
                .productName("New Product")
                .licenseType("personal")
                .build();

        License result = licenseService.createLicense(input);

        assertThat(result.getLicenseId()).isNotNull();
        assertThat(result.getLicenseId()).matches(
                "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
        assertThat(result.getComment()).isEqualTo(COMMENT);
        verify(licenseRepository).save(input);
    }

    // ------------------------------------------------------------------ updateLicense

    @Test
    @DisplayName("updateLicense: saves and returns license with comment")
    void updateLicense_savesAndReturns() {
        when(config.getProperty()).thenReturn(COMMENT);
        License result = licenseService.updateLicense(sampleLicense);

        verify(licenseRepository).save(sampleLicense);
        assertThat(result.getComment()).isEqualTo(COMMENT);
    }

    @Test
    @DisplayName("deleteLicense: calls delete and returns formatted message")
    void deleteLicense_callsDeleteWithCorrectId() {
        when(messages.getMessage(eq("license.delete.message"), isNull(), any(Locale.class)))
                .thenReturn("License %s deleted");

        String result = licenseService.deleteLicense(LICENSE_ID);

        assertThat(result).isEqualTo("License " + LICENSE_ID + " deleted");
        verify(licenseRepository).delete(argThat(l -> l.getLicenseId().equals(LICENSE_ID)));
    }

    @Test
    @DisplayName("getLicensesByOrganization: returns licenses from repository")
    void getLicensesByOrganization_returnsList() {
        when(licenseRepository.findByOrganizationId(ORG_ID))
                .thenReturn(List.of(sampleLicense));

        List<License> result = licenseService.getLicensesByOrganization(ORG_ID);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getProductName()).isEqualTo("Cloud Infrastructure Suite");
    }
}