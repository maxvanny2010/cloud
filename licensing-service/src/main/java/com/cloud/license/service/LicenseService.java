package com.cloud.license.service;

import com.cloud.license.config.ServiceConfig;
import com.cloud.license.model.License;
import com.cloud.license.repository.LicenseRepository;
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
    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;


    public LicenseService(final MessageSource messages,
                          final LicenseRepository licenseRepository,
                          final ServiceConfig config) {
        this.messages = messages;
        this.licenseRepository = licenseRepository;
        this.config = config;
    }


    public License getLicense(String licenseId, String organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (null == license) {
            throw new IllegalArgumentException(
                    String.format(messages.getMessage(
                                    "license.search.error.message", null, null),
                            licenseId, organizationId));
        }
        return license.withComment(config.getProperty());
    }

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    public String deleteLicense(String licenseId) {
        String responseMessage;
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        responseMessage = String.format(messages.getMessage(
                "license.delete.message", null, null), licenseId);
        return responseMessage;
    }
}
