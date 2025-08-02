package com.thought_hub.mapper;

import com.thought_hub.dto.GroupDto;
import com.thought_hub.entity.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

    public Group mapToGroup(GroupDto dto, Group group) {
        group.setGroupName(dto.getGroupName());
        group.setDescription(dto.getDescription());
        return group;
    }

    public GroupDto mapToGroupDto(Group group, GroupDto dto) {
        dto.setId(group.getId());
        dto.setGroupName(group.getGroupName());
        dto.setDescription(group.getDescription());
        dto.setCreatedAt(group.getCreatedAt());
        dto.setUpdatedAt(group.getUpdatedAt());
        dto.setCreatedBy(group.getCreatedBy());
        dto.setUpdatedBy(group.getUpdatedBy());
        return dto;
    }

}
