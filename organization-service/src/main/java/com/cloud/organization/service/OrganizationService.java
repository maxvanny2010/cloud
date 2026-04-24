package com.cloud.organization.service;

import com.cloud.organization.model.Organization;
import com.cloud.organization.repository.OrganizationRepository;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * OrganizationService.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@Service
public class OrganizationService {

    private final OrganizationRepository repository;

    public OrganizationService(final OrganizationRepository repository) {
        this.repository = repository;
    }

    public Organization findById(String organizationId) {
        return repository.findById(organizationId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Organization not found with id: " + organizationId));
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        return organization;

    }

    public void update(Organization organization) {
        repository.save(organization);
    }

    public void delete(String organizationId) {
        repository.deleteById(organizationId);
    }
}
