package com.share_thoughts.thoughts_sync.dto;


import com.share_thoughts.thoughts_sync.constant.ContentType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageDto {

    @Digits(integer = 10, fraction = 0, message = "ID should be a numeric value with up to 10 digits")
    private Long id;
    @NotNull(message = "Content type is required")
    private ContentType contentType;
    @NotEmpty(message = "Content is required")
    private String content;
    @NotNull(message = "Chat ID must not be null")
    @Digits(integer = 10, fraction = 0, message = "Chat ID should be a numeric value with up to 10 digits")
    private Long chatId;
    private LocalDateTime sentAt;
    private LocalDateTime editedAt;
}
