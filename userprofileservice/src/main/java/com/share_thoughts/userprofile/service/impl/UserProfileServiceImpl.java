package com.share_thoughts.userprofile.service.impl;

import com.share_thoughts.userprofile.dto.UserProfileDto;
import com.share_thoughts.userprofile.entity.UserProfile;
import com.share_thoughts.userprofile.exception.ResourceAlreadyExistException;
import com.share_thoughts.userprofile.exception.ResourceNotFoundException;
import com.share_thoughts.userprofile.mapper.UserProfileMapper;
import com.share_thoughts.userprofile.repository.UserProfileRepo;
import com.share_thoughts.userprofile.service.UserProfileService;
import com.share_thoughts.userprofile.util.UniqueIdGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepo userProfileRepo;
    private final UserProfileMapper mapper;
    public final UniqueIdGenerator generator;

    @Override
    public Long createUser(UserProfileDto dto) {
        UserProfile user = mapper.mapToUserProfile(dto, new UserProfile());

        Optional<UserProfile> byUserName = userProfileRepo.findByUsername(user.getUsername());
        Optional<UserProfile> byPhoneNumber = userProfileRepo.findByPhoneNumber(user.getPhoneNumber());
        if (byUserName.isPresent() || byPhoneNumber.isPresent())
        {
            String field = (byUserName.isPresent() && byPhoneNumber.isPresent())?
                    "phone number and username" : (byPhoneNumber.isPresent() ? "phone number" : "username");
            String value = (byUserName.isPresent() && byPhoneNumber.isPresent())?
                    dto.getName()+" and "+dto.getPhoneNumber() : (byPhoneNumber.isPresent() ? dto.getPhoneNumber().toString() : dto.getName());
            throw new ResourceAlreadyExistException("user", field, value);
        }
        user.setId(generator.generate10DigitId());
        user =userProfileRepo.save(user);
        return user.getId();
    }

    @Override
    public UserProfileDto getUserByPhoneNumber(Long phoneNumber) {

        UserProfile userProfile = userProfileRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User","PhoneNumber", phoneNumber.toString()));
        return mapper.mapToUserProfileDto(userProfile, new UserProfileDto());

    }

    @Override
    public UserProfileDto getUserByUserName(String userName) {

        UserProfile userProfile = userProfileRepo.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User","UserName", userName));
        return mapper.mapToUserProfileDto(userProfile, new UserProfileDto());

    }

    @Override
    public UserProfileDto getById(Long id) {
        UserProfile userProfile = userProfileRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","ID", id.toString()));
        return mapper.mapToUserProfileDto(userProfile, new UserProfileDto());
    }

    @Override
    public void updateUser(UserProfileDto dto) {
        if(dto.getId() == null)
            throw new ResourceNotFoundException("User", "ID", "Null");
        UserProfile user = userProfileRepo.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getId().toString()));

        if (dto.getPhoneNumber().longValue() != user.getPhoneNumber().longValue() && !dto.getName().equals(user.getUsername()))
        {
            Optional<UserProfile> byPhoneNumber = userProfileRepo.findByPhoneNumber(dto.getPhoneNumber());
            Optional<UserProfile> byUserName = userProfileRepo.findByUsername(dto.getName());
            if (byUserName.isPresent() || byPhoneNumber.isPresent())
            {
                String field = (byUserName.isPresent() && byPhoneNumber.isPresent())?
                        "phone number and username" : (byPhoneNumber.isPresent() ? "phone number" : "username");
                String value = (byUserName.isPresent() && byPhoneNumber.isPresent())?
                        dto.getName()+" and "+dto.getPhoneNumber() : (byPhoneNumber.isPresent() ? dto.getPhoneNumber().toString() : dto.getName());
                throw new ResourceAlreadyExistException("user", field, value);
            }
        }
        if (dto.getPhoneNumber().longValue() != user.getPhoneNumber().longValue())
        {
            Optional<UserProfile> byPhoneNumber = userProfileRepo.findByPhoneNumber(dto.getPhoneNumber());
            if (byPhoneNumber.isPresent())
                throw new ResourceAlreadyExistException("User", "PhoneNumber", dto.getPhoneNumber().toString());
        }
        if (!dto.getName().equals(user.getUsername()))
        {
            Optional<UserProfile> byUserName = userProfileRepo.findByUsername(dto.getName());
            if (byUserName.isPresent())
                throw new ResourceAlreadyExistException("User", "UserName", dto.getName());
        }

        UserProfile updatedUser = mapper.mapToUserProfile(dto, user);
        userProfileRepo.save(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUserByPhoneNumber(Long phoneNumber) {
        UserProfile byPhoneNumber = userProfileRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow(() ->new ResourceNotFoundException("User", "PhoneNumber", phoneNumber.toString()));
        userProfileRepo.deleteByPhoneNumber(phoneNumber);
    }


}
