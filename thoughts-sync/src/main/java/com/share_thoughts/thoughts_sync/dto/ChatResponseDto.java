package com.share_thoughts.thoughts_sync.dto;

import com.share_thoughts.thoughts_sync.constant.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChatResponseDto {

    private Long chatId;
    private Long messageId;
    private Long senderId;
    private String content;
    private ContentType contentType;
    private LocalDateTime sentAt;
    private LocalDateTime editedAt;
}
