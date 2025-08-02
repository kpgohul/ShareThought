package com.share_thoughts.thoughts_sync.dto;

import com.share_thoughts.thoughts_sync.constant.ContentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneToOneChatDto {

    private String content;
    @Enumerated(EnumType.STRING)
    private ContentType contentType;

}
