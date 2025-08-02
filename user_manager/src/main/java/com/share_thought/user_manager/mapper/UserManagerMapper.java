package com.share_thought.user_manager.mapper;

import com.share_thought.user_manager.dto.UserCreateDto;
import com.share_thought.user_manager.dto.UserManagerDto;
import com.share_thought.user_manager.dto.UserUpdateDto;
import com.share_thought.user_manager.entity.UserManager;
import org.springframework.stereotype.Component;

@Component
public class UserManagerMapper {

    public UserManager mapToUserManger(UserCreateDto dto, UserManager userManager) {
        userManager.setUserName(dto.getFirstName()+" "+dto.getLastName());
        userManager.setStatus(dto.getStatus());
        userManager.setAbout(dto.getAbout());
        return userManager;
    }

    public UserManager mapToUserManger(UserUpdateDto dto, UserManager userManager) {
        userManager.setUserName(dto.getFirstName()+" "+dto.getLastName());
        userManager.setStatus(dto.getStatus());
        userManager.setAbout(dto.getAbout());
        return userManager;
    }

    public UserManagerDto mapToUserManagerDto(UserManager userManager, UserManagerDto dto) {
        dto.setId(userManager.getId());
        dto.setKeycloakId(userManager.getKeycloakId());
        dto.setStatus(userManager.getStatus());
        dto.setAbout(userManager.getAbout());
        dto.setFirstName(userManager.getUserName().split(" ")[0]);
        dto.setLastName(userManager.getUserName().split(" ")[1]);
        dto.setAbout(userManager.getAbout());
        dto.setStatus(userManager.getStatus());
        dto.setCreatedAt(userManager.getCreatedAt());
        dto.setUpdatedAt(userManager.getUpdatedAt());
        return dto;
    }
}
