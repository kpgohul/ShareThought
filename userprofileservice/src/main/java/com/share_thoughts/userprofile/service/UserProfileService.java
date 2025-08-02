package com.share_thoughts.userprofile.service;

import com.share_thoughts.userprofile.dto.UserProfileDto;

public interface UserProfileService {
    Long createUser( UserProfileDto dto);

    UserProfileDto getUserByPhoneNumber(Long phoneNumber);

    UserProfileDto getUserByUserName(String userName);

    void updateUser(UserProfileDto dto);

    UserProfileDto getById(Long id);

    void deleteUserByPhoneNumber(Long phoneNumber);
}
