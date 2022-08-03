package com.cloud.organization.controller;

import com.cloud.organization.model.Organization;
import com.cloud.organization.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrganisationController.
 *
 * @author legion
 * @version 5.0
 * @since 03.08.2022
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
            @PathVariable("organizationId") String organizationId) {
        return ResponseEntity.ok(service.findById(organizationId));
    }

    @PutMapping(value = "/{organizationId}")
    public void updateOrganization(@PathVariable("organizationId") String id,
                                   @RequestBody Organization organization) {
        service.update(organization);
    }

    @PostMapping
    public ResponseEntity<Organization> saveOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(service.create(organization));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{organizationId}")
    public void deleteOrganization(@PathVariable("id") String id,
                                   @RequestBody Organization organization) {
        service.delete(organization);
    }

}

