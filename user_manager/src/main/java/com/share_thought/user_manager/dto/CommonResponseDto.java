package com.share_thought.user_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponseDto {

    private String statusCode;

    private String message;
}
