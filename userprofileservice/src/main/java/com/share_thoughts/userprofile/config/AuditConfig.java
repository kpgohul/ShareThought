package com.share_thoughts.userprofile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditConfig    {

    @Bean
    public AuditorAware<String> auditorAware() {
        // For now, returning a fixed value "system" as the auditor
        // In a real application, you would typically get this from Spring Security's SecurityContext
        return () -> Optional.of("system");
    }
}
