package com.share_thoughts.thoughts_sync.repository;

import com.share_thoughts.thoughts_sync.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {

    Optional<Chat> findByIdAndSenderId(Long chatId, Long senderId);
    Optional<List<Chat>> findAllByOtoId(Long chatId);
    Optional<List<Chat>> findAllByGroupId(Long groupId);
}
