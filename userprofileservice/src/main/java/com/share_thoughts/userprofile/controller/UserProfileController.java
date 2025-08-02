package com.share_thoughts.userprofile.controller;

import com.share_thoughts.userprofile.constrain.DigitLength;
import com.share_thoughts.userprofile.dto.CommonResponseDto;
import com.share_thoughts.userprofile.dto.UserCreatedResponseDto;
import com.share_thoughts.userprofile.dto.UserProfileDto;
import com.share_thoughts.userprofile.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class UserProfileController {

    private final UserProfileService userProfileService;


    @PostMapping
    public ResponseEntity<UserCreatedResponseDto> createUser(@Valid @RequestBody UserProfileDto dto)
    {
        Long id = userProfileService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserCreatedResponseDto(id));
    }

    @GetMapping
    public ResponseEntity<UserProfileDto> getUser(
            @RequestParam(required = false) Long phone_number,
            @RequestParam(required = false) String username) {
            
        if (phone_number != null) {
            UserProfileDto dto = userProfileService.getUserByPhoneNumber(phone_number);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } else if (username != null) {
            UserProfileDto dto = userProfileService.getUserByUserName(username);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } else {
            throw new IllegalArgumentException("Either phoneNumber or userName must be provided");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserById(@Valid @PathVariable Long id) {
        UserProfileDto dto = userProfileService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserProfileDto dto)
    {
        userProfileService.updateUser(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.toString(), "User::"+dto.getId()+" updated successfully"));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByPhoneNumber(@RequestParam @DigitLength(length = 10, message = "Phone number must be exactly 10 digits")
                                                     Long phone_number)
    {
        userProfileService.deleteUserByPhoneNumber(phone_number);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.toString(), "User::"+phone_number+" deleted successfully"));
    }
}
