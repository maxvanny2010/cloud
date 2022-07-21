package com.cloud.license.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ServiceConfig.
 *
 * @author legion
 * @version 5.0
 * @since 19.07.2022
 */
@Configuration
@ConfigurationProperties(prefix = "example")
public class ServiceConfig {
    @Setter
    @Getter
    private String property;

}
