package com.cloud.license.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * License.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "licenses")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class License extends RepresentationModel<License> {
    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;
    private String description;
    @Column(name = "organization_id", nullable = false)
    private String organizationId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "license_type", nullable = false)
    private String licenseType;
    @Column(name = "comment")
    private String comment;
    @Transient
    private String organizationName;
    @Transient
    private String contactName;
    @Transient
    private String contactPhone;
    @Transient
    private String contactEmail;

    public License withComment(String comment) {
        this.setComment(comment);
        return this;
    }
}
