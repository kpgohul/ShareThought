package com.share_thought.user_manager.client;

import com.share_thought.user_manager.dto.UserCreateDto;
import com.share_thought.user_manager.dto.UserManagerDto;
import com.share_thought.user_manager.dto.UserUpdateDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Component
public class KeyCloakClient {

    @Value("${keycloak.auth-server-url}")
    private String authURL;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Value("${keycloak.default-role-id}")
    private String defaultRoleID;
    @Value("${keycloak.default-role}")
    private String defaultRole;
    private final RestTemplate restTemplate = new RestTemplate();



    private String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        String tokenUrl = authURL + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers); // <1>>

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class); // <2>>
        return  Objects.requireNonNull(response.getBody()).get("access_token").toString();
    }

    public String  createUserInKeycloak(UserCreateDto createRequest) {
        String accessToken = getAccessToken();
        String url = authURL + "/admin/realms/" + realm + "/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> user = new HashMap<>();
        user.put("username", createRequest.getPhoneNumber());
        user.put("enabled", true);
        user.put("email", createRequest.getEmail());
        user.put("firstName", createRequest.getFirstName());
        user.put("lastName", createRequest.getLastName());

        Map<String, String> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", createRequest.getPassword());
        credentials.put("temporary", "false");

        user.put("credentials", Collections.singletonList(credentials));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            URI location = response.getHeaders().getLocation();
            String[] segment = location.getPath().split("/");
            String keyCloakUserId = segment[segment.length - 1];
            return keyCloakUserId;
        }
        throw new RuntimeException("Failed to create user in Keycloak");
    }

    public void assignDefaultRoleToUser(String userId) {
        String accessToken = getAccessToken();
        String url = authURL + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<Map<String, Object>> body = List.of(
                Map.of(
                        "id", defaultRoleID,
                        "name", defaultRole
        ));
        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to assign role::"+defaultRole+" user::"+ userId+" in Keycloak");
        }

    }

    public void updateUserInKeycloak(UserUpdateDto updateRequest) {
        String accessToken = getAccessToken();
        String url = authURL + "/admin/realms/" + realm + "/users/" + updateRequest.getKeycloakId();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("firstName", updateRequest.getFirstName());
        body.put("lastName", updateRequest.getLastName());
        body.put("email", updateRequest.getEmail());
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to update user in Keycloak");
        }

    }

    public Map<String, Object> getUserDetailsUsingKeyCloakId(String Id)
    {
        String accessToken = getAccessToken();
        String url =    authURL + "/admin/realms/" + realm + "/users/" + Id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
        return response.getBody();

    }

    public void updatePasswordInKeycloak(String userId, String newPassword) {
        String accessToken = getAccessToken();
        String url = authURL + "/admin/realms/" + realm + "/users/" + userId + "/reset-password";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("type", "password");
        body.put("value", newPassword);
        body.put("temporary", false);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to update password in Keycloak");
        }
    }


    public void deleteUserInKeycloak(String userId) {
        String accessToken = getAccessToken();
        String url = authURL + "/admin/realms/" + realm + "/users/" + userId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
        if(!response.getStatusCode().is2xxSuccessful())
        {
            throw new RuntimeException("Failed to delete user in Keycloak");
        }
    }
}
