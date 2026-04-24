package com.cloud.organization.repository;

import com.cloud.organization.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * OrganizationRepository.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
}
