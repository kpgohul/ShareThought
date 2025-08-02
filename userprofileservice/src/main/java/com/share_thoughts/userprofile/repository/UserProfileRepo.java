package com.share_thoughts.userprofile.repository;

import com.share_thoughts.userprofile.entity.UserProfile;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {


    Optional<UserProfile> findByUsername(String username);
    Optional<UserProfile> findByPhoneNumber(Long phoneNumber);
    Optional<UserProfile> findById(Long id);
    void deleteByPhoneNumber(Long phoneNumber);


}
