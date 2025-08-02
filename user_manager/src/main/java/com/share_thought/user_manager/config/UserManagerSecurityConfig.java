package com.share_thought.user_manager.config;

import com.share_thought.user_manager.exception.BasicAuthenticationEntryPoint;
import com.share_thought.user_manager.exception.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.PREFERRED_USERNAME;

@Configuration
@EnableWebSecurity
public class UserManagerSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
        http
                .authorizeHttpRequests(req -> req
//                        .requestMatchers(HttpMethod.GET, "/user/me").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/user").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/user/**").hasAnyRole("User", "Admin")
//                        .requestMatchers(HttpMethod.PUT, "/user/**").hasAnyRole("User", "Admin")
//                        .requestMatchers(HttpMethod.DELETE, "/user/**").hasAnyRole("User", "Admin", "Super Admin")
                        // Permit everything else
                        .anyRequest().permitAll()
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint(new BasicAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConvertor());
//        jwtAuthenticationConverter.setPrincipalClaimName(PREFERRED_USERNAME);
        return jwtAuthenticationConverter;
    }
}

