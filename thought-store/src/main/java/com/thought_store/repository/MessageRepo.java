package com.thought_store.repository;

import com.thought_store.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, Long> {

    Message findByChatId(Long chatId);

    Optional<Message> findByIdAndChatId(Long id, Long chatId);

    void deleteAllByChatId(Long chatId);
}
