package com.share_thoughts.thoughts_sync.client;

import com.share_thoughts.thoughts_sync.dto.GroupDto;
import com.share_thoughts.thoughts_sync.util.RequestUtil;
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
public class GroupClient {

    private final RequestUtil requestUtil;
    private final RestTemplate restTemplate;
//    private String baseUrl = "http://thought-hub";
    private String baseUrl = "http://thought-hub/groups";


    public GroupDto getGroupInfo(Long groupId) {
        log.info("Getting group info :: {}", groupId);
        String url = baseUrl;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create(url));
        builder.queryParam("id", groupId);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(requestUtil.getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<GroupDto> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                request,
                GroupDto.class
        );
        if (!response.getStatusCode().is2xxSuccessful())
            throw new RuntimeException("Failed to get group info!");
        return response.getBody();


    }


}
