package com.share_thought.user_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IDAloneResponse {

    private Long id;
    private String keycloakId;
}
