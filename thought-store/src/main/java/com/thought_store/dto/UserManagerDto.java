package com.thought_store.dto;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserManagerDto {

    private Long id;
    
    @NotEmpty(message = "Keycloak ID is required")
    private String keycloakId;
    
    @NotEmpty(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
    private String phoneNumber;
    
    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    
    @NotEmpty(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @NotEmpty(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @Size(max = 500, message = "About section cannot exceed 500 characters")
    private String about;
    
    @NotEmpty(message = "Status is required")
    private String status;
    
    // These should be managed by JPA
    @Transient
    private LocalDateTime createdAt;
    
    @Transient
    private LocalDateTime updatedAt;

}
