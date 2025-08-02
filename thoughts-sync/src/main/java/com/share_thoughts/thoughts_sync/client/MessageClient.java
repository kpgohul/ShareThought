package com.share_thoughts.thoughts_sync.client;

import com.share_thoughts.thoughts_sync.dto.CreateMessageDto;
import com.share_thoughts.thoughts_sync.dto.MessageDto;
import com.share_thoughts.thoughts_sync.dto.UpdateMessageRequestDto;
import com.share_thoughts.thoughts_sync.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageClient {

    private final RequestUtil requestUtil;
    private final RestTemplate restTemplate;

    private String baseUrl = "http://thought-store/messages";

    public CreateMessageDto storeMessage(MessageDto dto)
    {
        log.info("Request received for message creation ::{}",dto);
        String url = baseUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(requestUtil.getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MessageDto> httpEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<CreateMessageDto> create = restTemplate.exchange(url, HttpMethod.POST, httpEntity, CreateMessageDto.class);
        if (!create.getStatusCode().is2xxSuccessful())
            throw new RuntimeException("Failed to create message!");
        return create.getBody();
    }

    public void deleteMessage(Long messageId, Long chatId)
    {
        log.info("Request received for message deletion - messageId ::{} and chatId ::{}",messageId,chatId);
        String url = baseUrl;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(URI.create(url));
        if (chatId != null) uriBuilder.queryParam("chat_id", chatId);
        if (messageId != null) uriBuilder.queryParam("message_id", messageId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(requestUtil.getBearerToken());
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Void> res = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.DELETE, request,
                Void.class);
        if (!res.getStatusCode().is2xxSuccessful())
            throw new RuntimeException("Failed to delete message!");
    }

    public MessageDto getMessage(Long chatId, Long messageId)
    {
        log.info("Request received for get message - chatId ::{} and messageId ::{}",chatId,messageId);
        String url = baseUrl;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create(url));
        if (chatId != null) builder.queryParam("chat_id", chatId);
        if (messageId != null) builder.queryParam("message_id", messageId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(requestUtil.getBearerToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<MessageDto> res = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                MessageDto.class
        );
        if (!res.getStatusCode().is2xxSuccessful())
            throw new RuntimeException("Failed to get messages!");
        return res.getBody();
    }

    public void updateMessage(Long chatId, Long messageId, UpdateMessageRequestDto messageDto)
    {
        log.info("Request received for update message - chatId ::{} and messageId ::{}",chatId,messageId);
        String url = baseUrl;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create(url));
        if (chatId != null) builder.queryParam("chat_id", chatId);
        if (messageId != null) builder.queryParam("message_id", messageId);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(requestUtil.getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateMessageRequestDto> request = new HttpEntity<>(messageDto, headers);
        ResponseEntity<Void> res = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.PUT,
                request,
                Void.class
        );
        if (!res.getStatusCode().is2xxSuccessful())
            throw new RuntimeException("Failed to update message!");
    }




}
