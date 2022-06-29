package com.cloud.license.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * License.
 *
 * @author legion
 * @version 5.0
 * @since 29.06.2022
 */
@Getter
@Setter
@ToString
@Builder
public class License {
    private int id;
    private String licenseId;
    private String description;
    private String organizationId;
    private String productName;
    private String licenseType;
}
