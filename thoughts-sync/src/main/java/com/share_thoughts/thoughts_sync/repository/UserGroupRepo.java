package com.share_thoughts.thoughts_sync.repository;

import com.share_thoughts.thoughts_sync.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupRepo extends JpaRepository<UserGroup, Long> {

    Optional<UserGroup> findByGroupIdAndUserId(Long groupId, Long userId);

    Optional<List<UserGroup>> findAllByGroupId(Long groupId);
}
