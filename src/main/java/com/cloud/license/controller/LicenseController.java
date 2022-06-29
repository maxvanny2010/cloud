package com.cloud.license.controller;

import com.cloud.license.model.License;
import com.cloud.license.service.LicenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * LicenseController.
 *
 * @author legion
 * @version 5.0
 * @since 29.06.2022
 */
@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {
    private final LicenseService licenseService;

    public LicenseController(final LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public ResponseEntity<License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId
    ) {
        License license = licenseService.getLicense(licenseId, organizationId);
        //вспомогательный класс, предназначенный для создания ссылок на классы контроллеров
        license.add(linkTo(methodOn(LicenseController.class)
                        .getLicense(organizationId, license.getLicenseId()))
                        .withSelfRel(),//
                linkTo(methodOn(LicenseController.class)
                        .createLicense(organizationId, license, null))
                        .withRel("createLicense"),//
                linkTo(methodOn(LicenseController.class)
                        .updateLicense(organizationId, license, null))
                        .withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class)
                        .deleteLicense(organizationId, license.getLicenseId()))
                        .withRel("deleteLicense"));
        return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<String> updateLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License request,
            @RequestHeader(value = "Accept-Language", required = false) Locale locale
    ) {
        return ResponseEntity.ok(licenseService.updateLicense(request, organizationId, locale));
    }

    /**
     * если клиентское приложение поддерживает локали,
     * то лучший вариант – получение локали через параметр метода контроллера.
     * Но если управление локалью осуществляется на
     * сервере, то вы можете использовать локаль по умолчанию.
     */
    @PostMapping
    public ResponseEntity<String> createLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License request,
            @RequestHeader(value = "Accept-Language", required = false) Locale locale
    ) {
        return ResponseEntity.ok(licenseService.createLicense(request, organizationId, locale));
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organizationId));
    }
}
