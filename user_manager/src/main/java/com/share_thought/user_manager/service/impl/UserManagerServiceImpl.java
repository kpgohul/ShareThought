package com.share_thought.user_manager.service.impl;

import com.share_thought.user_manager.client.KeyCloakClient;
import com.share_thought.user_manager.dto.IDAloneResponse;
import com.share_thought.user_manager.dto.UserCreateDto;
import com.share_thought.user_manager.dto.UserManagerDto;
import com.share_thought.user_manager.dto.UserUpdateDto;
import com.share_thought.user_manager.entity.UserManager;
import com.share_thought.user_manager.exception.ResourceNotFoundException;
import com.share_thought.user_manager.mapper.UserManagerMapper;
import com.share_thought.user_manager.repository.UserManagerRepo;
import com.share_thought.user_manager.service.UserManagerService;
import com.share_thought.user_manager.util.UniqueIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserManagerServiceImpl implements UserManagerService {

    private final KeyCloakClient client;
    private final UserManagerRepo repo;
    private final UserManagerMapper mapper;
    private final UniqueIdGenerator generator;

    @Override
    public IDAloneResponse createUser(UserCreateDto request) {
        String keycloakId = client.createUserInKeycloak(request);
        client.assignDefaultRoleToUser(keycloakId);
        UserManager userManager = mapper.mapToUserManger(request, new UserManager());
        userManager.setKeycloakId(keycloakId);
        userManager.setId(generator.generate10DigitId());
        userManager.setUserName(request.getFirstName()+" "+request.getLastName());
        Long id  = repo.save(userManager).getId();
        return new IDAloneResponse(id, keycloakId);
    }

    @Override
    public void updateUser(UserUpdateDto request) {
        client.updateUserInKeycloak(request);
        UserManager pastUser = repo.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getId().toString()));
        if(request.getPassword() != null)
            client.updatePasswordInKeycloak(request.getKeycloakId(), request.getPassword());
        UserManager updatedUser = mapper.mapToUserManger(request, pastUser);
        repo.save(updatedUser);
    }

    @Override
    public UserManagerDto getUserByKeycloakId(String keycloakId) {
        Map<String, Object> KeycloakUser = client.getUserDetailsUsingKeyCloakId(keycloakId);
        if (KeycloakUser == null)
            throw new ResourceNotFoundException("User", "keycloakId", keycloakId);
        UserManager userManager = repo.findByKeycloakId(keycloakId);
        if (userManager == null)
            throw new ResourceNotFoundException("User", "KeycloakId", keycloakId);
        UserManagerDto dto = mapper.mapToUserManagerDto(userManager, new UserManagerDto());
        dto.setFirstName(KeycloakUser.get("firstName").toString());
        dto.setLastName(KeycloakUser.get("lastName").toString());
        dto.setEmail(KeycloakUser.get("email").toString());
        dto.setPhoneNumber(KeycloakUser.get("username").toString());

        return dto;
    }

    @Override
    public UserManagerDto getUserById(Long id) {
        UserManager userManager = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        Map<String, Object> KeycloakUser = client.getUserDetailsUsingKeyCloakId(userManager.getKeycloakId());
        if (KeycloakUser == null)
            throw new ResourceNotFoundException("User", "keycloakId", userManager.getKeycloakId());
        UserManagerDto dto = mapper.mapToUserManagerDto(userManager, new UserManagerDto());
        dto.setFirstName(KeycloakUser.get("firstName").toString());
        dto.setLastName(KeycloakUser.get("lastName").toString());
        dto.setEmail(KeycloakUser.get("email").toString());
        dto.setPhoneNumber(KeycloakUser.get("username").toString());
        return dto;
    }

    @Override
    public IDAloneResponse deleteUserByKeycloakId(String keycloakId) {
        client.deleteUserInKeycloak(keycloakId);
        UserManager userManager = repo.findByKeycloakId(keycloakId);
        if (userManager == null)
            throw new ResourceNotFoundException("User", "keycloakId", keycloakId);
        Long id = userManager.getId();
        repo.delete(userManager);
        return new IDAloneResponse(id, keycloakId);
    }

    @Override
    public IDAloneResponse deleteUserById(Long id) {
        UserManager userManager = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        String keycloakId = userManager.getKeycloakId();
        client.deleteUserInKeycloak(keycloakId);
        repo.delete(userManager);
        return new IDAloneResponse(id, keycloakId);
    }

    @Override
    public UserManagerDto getMe() {
        String keycloakId = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> KeycloakUser = client.getUserDetailsUsingKeyCloakId(keycloakId);
        if (KeycloakUser == null)
            throw new ResourceNotFoundException("User", "keycloakId", keycloakId);
        UserManager userManager = repo.findByKeycloakId(keycloakId);
        if (userManager == null)
            throw new ResourceNotFoundException("User", "KeycloakId", keycloakId);
        UserManagerDto dto = mapper.mapToUserManagerDto(userManager, new UserManagerDto());
        dto.setFirstName(KeycloakUser.get("firstName").toString());
        dto.setLastName(KeycloakUser.get("lastName").toString());
        dto.setEmail(KeycloakUser.get("email").toString());
        dto.setPhoneNumber(KeycloakUser.get("username").toString());
        return dto;

    }

//    @Override
//    public UserManagerDto getUserByPhoneNumber(String phNumber) {
//
//    }
}
