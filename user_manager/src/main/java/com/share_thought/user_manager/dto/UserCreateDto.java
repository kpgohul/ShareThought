package com.share_thought.user_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDto {

    @NotEmpty(message = "phone number is required")
    @Size(min = 10, message = "phone number must be at least 10 characters")
    private String phoneNumber;
    @NotEmpty(message = "email is required")
    @Email
    private String email;
    @NotEmpty(message = "password is required")
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;
    @Size(min = 4, message = "first name must be at least 4 characters")
    private String firstName;
    @Size(min = 1, message = "last name must be at least 1 characters")
    private String lastName;
    private String about;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
