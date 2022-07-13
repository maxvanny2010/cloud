package com.cloud.license.service;

import com.cloud.license.model.License;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * LicenseService.
 *
 * @author legion
 * @version 5.0
 * @since 29.06.2022
 */
@Service
public class LicenseService {
    private final MessageSource messages;

    public LicenseService(final MessageSource messages) {
        this.messages = messages;
    }

    public License getLicense(String licenseId, String organizationId) {
        return License.builder()
                .id(new Random().nextInt(1000))
                .licenseId(licenseId)
                .organizationId(organizationId)
                .description("Software product")
                .productName("cloud")
                .licenseType("full")
                .build();
    }

    public String createLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format(
                    messages.getMessage("license.create.message", null, locale),
                    license);
        }
        return responseMessage;
    }

    public String updateLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            //использует пустую локаль для выбора сообщения
            //будет использовать локаль по умолчанию,
            //которую задали в классе инициализации
            responseMessage = String.format(
                    messages.getMessage("license.update.message", null, null),
                    license);
        }
        return responseMessage;
    }

    public String deleteLicense(String licenseId, String organizationId) {
        return String.format(
                "Deleting license with id %s for the organization %s",
                licenseId, organizationId);

    }
}
