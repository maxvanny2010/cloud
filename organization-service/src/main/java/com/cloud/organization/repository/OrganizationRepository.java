package com.cloud.organization.repository;

import com.cloud.organization.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * OrganizationRepository.
 *
 * @author legion
 * @version 5.0
 * @since 03.08.2022
 */
@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
    Optional<Organization> findById(String organizationId);
}
