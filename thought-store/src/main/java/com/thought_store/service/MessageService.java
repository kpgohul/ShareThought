package com.thought_store.service;

import com.thought_store.dto.MessageDto;
import com.thought_store.dto.UpdateMessageDto;

import java.util.List;

public interface MessageService {
    Long storeMessage(MessageDto messageDto);

    MessageDto getMessageByChatId(Long chatId);

    MessageDto getMessageByMessageId(Long messageId);

//    void updateMessage(UpdateMessageDto messageDto);

    void deleteMessageByChatId(Long chatId);

    void deleteMessageByMessageAndChatId(Long messageId, Long chatId);

    void deleteMessageByMessageId(Long messageId);

    MessageDto getMessagesByChatIdAndMessageId(Long chatId, Long messageId);

    void updateMessageById(Long messageId, UpdateMessageDto messageDto);

    void updateMessageByChatId(Long chatId, UpdateMessageDto messageDto);

    void updateMessageByChatIdAndMessageId(Long chatId, Long messageId, UpdateMessageDto messageDto);
}
