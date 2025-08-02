package com.share_thought.user_manager.repository;

import com.share_thought.user_manager.entity.UserManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagerRepo extends JpaRepository<UserManager, Long> {

    UserManager findByKeycloakId(String keycloakId);
}
