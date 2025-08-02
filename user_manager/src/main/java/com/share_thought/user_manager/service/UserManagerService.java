package com.share_thought.user_manager.service;

import com.share_thought.user_manager.dto.IDAloneResponse;
import com.share_thought.user_manager.dto.UserCreateDto;
import com.share_thought.user_manager.dto.UserManagerDto;
import com.share_thought.user_manager.dto.UserUpdateDto;
import com.share_thought.user_manager.exception.ResourceNotFoundException;
import jakarta.validation.Valid;

public interface UserManagerService {
    /**
     * Creates a new user in the system and Keycloak
     * @param request User creation details
     * @return Response containing both database ID and Keycloak ID
     */
    public IDAloneResponse createUser(@Valid UserCreateDto request);

    /**
     * Updates an existing user's information
     * @param request User update details
     */
    public void updateUser(@Valid UserUpdateDto request);

    /**
     * Retrieves user details by Keycloak ID
     * @param keycloakId User's Keycloak ID
     * @return User details
     * @throws ResourceNotFoundException if user not found
     */
    public UserManagerDto getUserByKeycloakId(String keycloakId);

    /**
     * Retrieves user details by database ID
     * @param id User's database ID
     * @return User details
     * @throws ResourceNotFoundException if user not found
     */
    public UserManagerDto getUserById(Long id);

    /**
     * Deletes a user by Keycloak ID
     * @param keycloakId User's Keycloak ID
     * @return Response containing both database ID and Keycloak ID
     * @throws ResourceNotFoundException if user not found
     */
    public IDAloneResponse deleteUserByKeycloakId(String keycloakId);

    /**
     * Deletes a user by database ID
     * @param id User's database ID
     * @return Response containing both database ID and Keycloak ID
     * @throws ResourceNotFoundException if user not found
     */
    public IDAloneResponse deleteUserById(Long id);

    UserManagerDto getMe();

//    UserManagerDto getUserByPhoneNumber(String phNumber);
}
