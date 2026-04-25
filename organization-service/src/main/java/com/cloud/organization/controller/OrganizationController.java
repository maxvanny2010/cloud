package com.cloud.organization.controller;

import com.cloud.organization.model.Organization;
import com.cloud.organization.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * OrganisationController.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@RestController
@RequestMapping(value = "v1/organization")
public class OrganizationController {
    private final OrganizationService service;

    public OrganizationController(final OrganizationService service) {
        this.service = service;
    }


    @GetMapping(value = "/{organizationId}")
    public ResponseEntity<Organization> getOrganization(
            @PathVariable String organizationId) {
        return ResponseEntity.ok(service.findById(organizationId));
    }

    @PutMapping(value = "/{organizationId}")
    public void updateOrganization(@PathVariable String organizationId,
                                   @RequestBody Organization organization) {
        organization.setId(organizationId);
        service.update(organization);
    }

    @PostMapping
    public ResponseEntity<Organization> saveOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(service.create(organization));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{organizationId}")
    public void deleteOrganization(@PathVariable String organizationId) {
        service.delete(organizationId);
    }

}

