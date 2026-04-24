package com.cloud.license.service.client;

import com.cloud.license.model.Organization;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * OrganizationRestTemplateClient.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@Component
public class OrganizationRestTemplateClient {
    private final RestTemplate restTemplate;

    public OrganizationRestTemplateClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://organization/v1/organization/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
