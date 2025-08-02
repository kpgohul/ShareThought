package com.thought_hub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponseDto {

    private String statusCode;

    private String message;
}
