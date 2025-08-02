package com.thought_store.client;


import com.thought_store.dto.UserManagerDto;
import com.thought_store.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserManagerClient {

    private final RequestUtil requestUtil;
    private final RestTemplate restTemplate;

    private String baseUrl = "http://user-manager/users";

    public UserManagerDto getMe()
    {
        String url = baseUrl + "/me";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(requestUtil.getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<UserManagerDto> dto =  restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                UserManagerDto.class
        );
        if (!dto.getStatusCode().is2xxSuccessful())
                throw new RuntimeException("Failed to get user by token!");
        return dto.getBody();
    }

    public UserManagerDto getUserByKeycloakIdOrUserId(String keycloakId, String userId) {
        log.info("Getting user by keycloakId or userId :: {} {}", keycloakId, userId);
        String url = baseUrl;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create(url));
         if(userId != null) builder.queryParam("user_id", userId);
         else if (keycloakId != null) builder.queryParam("keycloak_id", keycloakId);
         HttpHeaders headers = new HttpHeaders();
         headers.setBearerAuth(requestUtil.getBearerToken());
         headers.setContentType(MediaType.APPLICATION_JSON);
         HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UserManagerDto> dto = restTemplate.exchange(
                 builder.toUriString(),
                 HttpMethod.GET,
                 entity,
                 UserManagerDto.class
         );
        if (!dto.getStatusCode().is2xxSuccessful())
        {
            throw new RuntimeException("Failed to get user by keycloakId or userId!");
        }
        return dto.getBody();
    }



}
