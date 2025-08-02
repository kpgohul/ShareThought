package com.share_thoughts.userprofile.dto;

import com.share_thoughts.userprofile.constrain.DigitLength;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserProfileDto {

    @Digits(integer = 10, fraction = 0, message = "ID should be a numeric value with up to 10 digits")
    private Long id;

    private String keyCloakId;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 4 , max = 15, message = "Username must be between 4 and 15 characters")
    private String name;

    @NotNull(message = "Phone number cannot be null")
    @DigitLength(length = 10, message = "Phone number must be exactly 10 digits")
    private Long phoneNumber;

    @Size(max = 250, message = "About can't exceed 250 characters")
    private String about;

    @Size(min = 2, message = "Status should be meaningful")
    private String status;
}
