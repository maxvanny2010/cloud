package com.cloud.license.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = "license")
@NoArgsConstructor
@AllArgsConstructor
public class License extends RepresentationModel<License> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    public License withComment(String comment) {
        this.setComment(comment);
        return this;
    }
}
