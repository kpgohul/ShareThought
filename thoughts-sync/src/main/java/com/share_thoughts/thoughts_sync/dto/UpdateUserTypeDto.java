package com.share_thoughts.thoughts_sync.dto;


import com.share_thoughts.thoughts_sync.constant.GroupRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateUserTypeDto {

    private Long user_id;
    private GroupRole role;
}
