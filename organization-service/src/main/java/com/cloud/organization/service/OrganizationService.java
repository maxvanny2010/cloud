package com.cloud.organization.service;

import com.cloud.organization.model.Organization;
import com.cloud.organization.repository.OrganizationRepository;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * OrganizationService.
 *
 * @author legion
 * @version 5.0
 * @since 03.08.2022
 */
@Service
public class OrganizationService {

    private final OrganizationRepository repository;

    public OrganizationService(final OrganizationRepository repository) {
        this.repository = repository;
    }

    public Organization findById(String organizationId) {
        Optional<Organization> opt = repository.findById(organizationId);
        return opt.orElse(null);
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        return organization;

    }

    public void update(Organization organization) {
        repository.save(organization);
    }

    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
    }
}
