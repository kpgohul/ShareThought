package com.share_thought.user_manager.controller;

import com.share_thought.user_manager.client.KeyCloakClient;
import com.share_thought.user_manager.dto.*;
import com.share_thought.user_manager.service.UserManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserManagerController {

    private final UserManagerService service;

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        UserManagerDto user = service.getMe();
        return ResponseEntity.ok().body(user);
    }


    @PostMapping
    public ResponseEntity<IDAloneResponse> createUser(@Valid @RequestBody UserCreateDto request)
    {
        log.info("Request received for user creation ::"+request);
        IDAloneResponse response = service.createUser(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponseDto> updateUser(@Valid @RequestBody UserUpdateDto request)
    {
        service.updateUser(request);
        return ResponseEntity.ok().body(new CommonResponseDto(HttpStatus.OK.toString(),"User updated successfully"));
    }

    @GetMapping
    public ResponseEntity<?> getUser(
            @RequestParam(required = false,name = "keycloak_id") String keycloakId,
            @RequestParam(required = false, name = "user_id") Long id)
    {
        log.info("Request received for get user info::"+"(keycloakId :: "+keycloakId+")" + ", (id :: "+id+")");
        if (keycloakId != null)
        {
            return ResponseEntity.ok().body(service.getUserByKeycloakId(keycloakId));
        }
        else if (id != null)
        {
            return ResponseEntity.ok().body(service.getUserById(id));
        }
        else throw new IllegalArgumentException("Either keycloakId or id must be provided");

    }

//    @GetMapping("/phone_number")
//    public ResponseEntity<?> getUserByPhoneNumber(
//            @RequestParam(required = true) String ph_number)
//    {
//        return ResponseEntity.ok().body(service.getUserByPhoneNumber(ph_number));
//    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(
            @RequestParam(required = false,name = "keycloak_id") String keycloakId,
            @RequestParam(required = false, name = "user_id") Long id)
    {
        log.info("Request received for delete user info::"+"(keycloakId :: "+keycloakId+")" + ", (id :: "+id+")");

        if (id != null) {
            IDAloneResponse response = service.deleteUserById(id);
            return ResponseEntity.ok().body(response);
        } else if (keycloakId != null) {
            IDAloneResponse response = service.deleteUserByKeycloakId(keycloakId);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(new CommonResponseDto(HttpStatus.BAD_REQUEST.toString(), "Either id or keycloakId must be provided"));
        }
    }


}
