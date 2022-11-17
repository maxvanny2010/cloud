package com.cloud.license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import java.util.*;

@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients
public class LicenseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicenseServiceApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //Когда искомое сообщение не обнаруживается, то этот вызов
        //гарантирует возврат кода сообщения 'license.creates.message'
        //вместо ошибки:"No message found under code 'license.creates.message' for locale 'es'
        messageSource.setUseCodeAsDefaultMessage(true);
        //устанавливает messages как базовое имя для файлов с переводами сообщений.
        //Например, в Италии мы бы использовали Locale.IT и файл с именем messages_it.properties.
        //Если сообщение на выбранном языке не будет найдено, то будет предпринята попытка
        //найти сообщение в файле по умолчанию, который называется messages.properties.
        messageSource.setBasenames("messages");
        return messageSource;
    }
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
