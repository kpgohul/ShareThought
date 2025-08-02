package com.share_thought.user_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

    private String apiPath;

    private String errorCode;

    private String errorMessage;

    private LocalDateTime errorTime;
}
