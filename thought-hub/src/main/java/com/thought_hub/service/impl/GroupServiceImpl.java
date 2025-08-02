package com.thought_hub.service.impl;

import com.thought_hub.client.UserGroupClient;
import com.thought_hub.dto.GroupDto;
import com.thought_hub.entity.Group;
import com.thought_hub.exception.ResourceNotFoundException;
import com.thought_hub.mapper.GroupMapper;
import com.thought_hub.repo.GroupRepo;
import com.thought_hub.service.GroupService;
import com.thought_hub.util.UniqueIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepo repo;
    private final UserGroupClient userGroupClient;
    private final UniqueIdGenerator generator;
    private final GroupMapper mapper;

    @Override
    public Long createGroup(GroupDto dto) {
        Group group = mapper.mapToGroup(dto, new Group());
        group.setId(generator.generate10DigitId());
        Group savedGroup = repo.save(group);
        userGroupClient.addUserToTheGroup(null,savedGroup.getId());
        return savedGroup.getId();
    }

    @Override
    public GroupDto getGroup(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("GROUP ID cannot be null");
        }
        Group group = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", id.toString()));
        return mapper.mapToGroupDto(group, new GroupDto());
    }

    @Override
    public void updateGroup(GroupDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("GROUP ID cannot be null");
        }
        Group group = repo.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", dto.getId().toString()));
        mapper.mapToGroup(dto, group);
        repo.save(group);
    }

    @Override
    public void deleteGroup(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("GROUP ID cannot be null");
        }
        Group group = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", id.toString()));
        repo.delete(group);
    }
}
