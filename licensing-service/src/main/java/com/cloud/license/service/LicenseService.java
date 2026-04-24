package com.cloud.license.service;

import com.cloud.license.config.ServiceConfig;
import com.cloud.license.model.License;
import com.cloud.license.model.Organization;
import com.cloud.license.repository.LicenseRepository;
import com.cloud.license.service.client.OrganizationDiscoveryClient;
import com.cloud.license.service.client.OrganizationFeignClient;
import com.cloud.license.service.client.OrganizationRestTemplateClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * LicenseService.
 *
 * @author maxvanny2010
 * @version 7.0
 * @since 22.04.2026
 */
@Service
public class LicenseService {

    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

    private final MessageSource messages;
    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;
    private final OrganizationFeignClient organizationFeignClient;
    private final OrganizationRestTemplateClient organizationRestClient;
    private final OrganizationDiscoveryClient organizationDiscoveryClient;

    public LicenseService(final MessageSource messages,
                          final LicenseRepository licenseRepository,
                          final ServiceConfig config,
                          final OrganizationFeignClient organizationFeignClient,
                          final OrganizationRestTemplateClient organizationRestClient,
                          final OrganizationDiscoveryClient organizationDiscoveryClient) {
        this.messages = messages;
        this.licenseRepository = licenseRepository;
        this.config = config;
        this.organizationFeignClient = organizationFeignClient;
        this.organizationRestClient = organizationRestClient;
        this.organizationDiscoveryClient = organizationDiscoveryClient;
    }

    public License getLicense(String licenseId, String organizationId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (null == license) {
            throw new IllegalArgumentException(
                    messages.getMessage("license.search.error.message",
                            new Object[]{licenseId, organizationId},
                            Locale.getDefault())
            );
        }
        Organization organization = retrieveOrganizationInfo(organizationId, clientType);
        if (null != organization) {
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactEmail());
            license.setContactPhone(organization.getContactPhone());
        }
        return license.withComment(config.getProperty());
    }

    private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
        return switch (clientType) {
            case "feign" -> {
                logger.info("Using feign client for organizationId={}", organizationId);
                yield organizationFeignClient.getOrganization(organizationId);
            }
            case "rest" -> {
                logger.info("Using rest client for organizationId={}", organizationId);
                yield organizationRestClient.getOrganization(organizationId);
            }
            case "discovery" -> {
                logger.info("Using discovery client for organizationId={}", organizationId);
                yield organizationDiscoveryClient.getOrganization(organizationId);
            }
            default -> {
                logger.info("Using default rest client for organizationId={}", organizationId);
                yield organizationRestClient.getOrganization(organizationId);
            }
        };
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
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        return String.format(
                messages.getMessage("license.delete.message", null, Locale.getDefault()),
                licenseId);
    }

    @CircuitBreaker(name = "license", fallbackMethod = "buildFallbackLicenseList")
    @RateLimiter(name = "license", fallbackMethod = "buildFallbackLicenseList")
    @Retry(name = "retryLicense", fallbackMethod = "buildFallbackLicenseList")
    @Bulkhead(name = "bulkheadLicense", type = Type.SEMAPHORE, fallbackMethod = "buildFallbackLicenseList")
    public List<License> getLicensesByOrganization(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    private List<License> buildFallbackLicenseList(String organizationId, Throwable t) {
        logger.warn("Fallback triggered for organizationId={}, reason={}", organizationId, t.getMessage());
        List<License> fallbackList = new ArrayList<>();
        License license = new License();
        license.setLicenseId("0000000-00-00000");
        license.setOrganizationId(organizationId);
        license.setProductName("Sorry no licensing information currently available");
        fallbackList.add(license);
        return fallbackList;
    }
}
