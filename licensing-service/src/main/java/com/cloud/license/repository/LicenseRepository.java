package com.cloud.license.repository;

import com.cloud.license.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LicenseRepository.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
    List<License> findByOrganizationId(String organizationId);

    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
