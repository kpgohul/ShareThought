package com.share_thoughts.thoughts_sync.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupDto {

    @Digits(integer = 10, fraction = 0, message = "ID should be a numeric value with up to 10 digits")
    private Long id;
    @NotEmpty(message = "Group name cannot be empty")
    private String GroupName;
    @NotEmpty(message = "Description cannot be empty")
    private String Description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;

}
