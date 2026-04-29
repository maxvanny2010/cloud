package com.cloud.license.controller;

import com.cloud.license.model.License;
import com.cloud.license.service.LicenseService;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * LicenseController.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@Validated
@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {
    private static final Logger logger = LoggerFactory.getLogger(LicenseController.class);
    private final LicenseService licenseService;

    public LicenseController(final LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable @Pattern(
                    regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$",
                    message = "Invalid UUID format"
            ) String organizationId,
            @PathVariable String licenseId) {

        License license = licenseService.getLicense(licenseId, organizationId, "");
        license.add(
                linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
                linkTo(methodOn(LicenseController.class).createLicense(license)).withRel("createLicense"),
                linkTo(methodOn(LicenseController.class).updateLicense(license)).withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class).deleteLicense(license.getLicenseId())).withRel("deleteLicense")
        );

        return ResponseEntity.ok(license);
    }

    @GetMapping(value = "/{licenseId}/{clientType}")
    public License getLicensesWithClient(
            @PathVariable String organizationId,
            @PathVariable String licenseId,
            @PathVariable String clientType) {
        return licenseService.getLicense(licenseId, organizationId, clientType);
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License request) {
        return ResponseEntity.ok(licenseService.updateLicense(request));
    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License request) {
        return ResponseEntity.ok(licenseService.createLicense(request));
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
    }

    @GetMapping(value = "/")
    public List<License> getLicenses(@PathVariable String organizationId)
            throws TimeoutException {
        return licenseService.getLicensesByOrganization(organizationId);
    }
}