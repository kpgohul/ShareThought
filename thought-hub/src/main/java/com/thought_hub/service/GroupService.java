package com.thought_hub.service;

import com.thought_hub.dto.GroupDto;


public interface GroupService {
    Long createGroup(GroupDto dto);

    GroupDto getGroup(Long id);

    void updateGroup(GroupDto dto);

    void deleteGroup(Long id);
}
