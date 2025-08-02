package com.share_thoughts.userprofile.mapper;

import com.share_thoughts.userprofile.dto.UserProfileDto;
import com.share_thoughts.userprofile.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfile mapToUserProfile(UserProfileDto dto, UserProfile userProfile){
        userProfile.setUsername(dto.getName());
        userProfile.setPhoneNumber(dto.getPhoneNumber());
        userProfile.setAbout(dto.getAbout());
        userProfile.setStatus(dto.getStatus());
        return userProfile;
    }

    public UserProfileDto mapToUserProfileDto(UserProfile userProfile, UserProfileDto dto){
        dto.setId(userProfile.getId());
        dto.setName(userProfile.getUsername());
        dto.setPhoneNumber(userProfile.getPhoneNumber());
        dto.setAbout(userProfile.getAbout());
        dto.setStatus(userProfile.getStatus());
        return dto;
    }

}
