package com.cloud.license.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ServiceConfig.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@Configuration
@ConfigurationProperties(prefix = "example")
public class ServiceConfig {
    @Setter
    @Getter
    private String property;

}
