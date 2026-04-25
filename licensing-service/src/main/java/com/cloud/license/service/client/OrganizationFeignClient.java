package com.cloud.license.service.client;

import com.cloud.license.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * OrganizationFeignClient.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@FeignClient("organization")
public interface OrganizationFeignClient {
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/v1/organization/{organizationId}",
            consumes = "application/json")
    Organization getOrganization(@PathVariable String organizationId);
}
