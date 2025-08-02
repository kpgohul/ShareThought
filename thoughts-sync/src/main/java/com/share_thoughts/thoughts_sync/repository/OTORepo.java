package com.share_thoughts.thoughts_sync.repository;

import com.share_thoughts.thoughts_sync.entity.OneToOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTORepo extends JpaRepository<OneToOne, Long> {

    Optional<OneToOne> findByUserId1AndUserId2(Long userId1, Long userId2);
}
