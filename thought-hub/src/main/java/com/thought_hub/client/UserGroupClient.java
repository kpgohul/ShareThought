package com.thought_hub.client;


import com.thought_hub.util.RequestUtil;
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
public class UserGroupClient {

    private final RequestUtil requestUtil;
    private final RestTemplate restTemplate;

    private String baseUrl = "http://thoughts-sync/chats";


    public void addUserToTheGroup(Long userId, Long groupId) {

        log.info("Request received for add user to the group - groupId ::{} and userId ::{}",groupId,userId);
        String url = baseUrl + "/add-user-to-group";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create(url));
        if(groupId == null)
            throw new RuntimeException("Group id is null");
        builder.queryParam("groupId", groupId);
        if(userId != null)
            builder.queryParam("userId", userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(requestUtil.getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Void> res = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                Void.class
        );
        if (!res.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to add user to the group!");
        }





    }



}
