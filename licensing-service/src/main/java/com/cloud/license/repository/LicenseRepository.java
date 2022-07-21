package com.cloud.license.repository;

import com.cloud.license.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * LicenseRepository.
 *
 * @author legion
 * @version 5.0
 * @since 19.07.2022
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
    List<License> findByOrganizationId(String organizationId);

    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
