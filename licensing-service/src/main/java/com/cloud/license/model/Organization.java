package com.cloud.license.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

/**
 * Organisation.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@Getter
@Setter
@ToString
public class Organization extends RepresentationModel<Organization> {

    String id;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;

}
