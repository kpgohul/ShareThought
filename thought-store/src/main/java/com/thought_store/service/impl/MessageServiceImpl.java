package com.thought_store.service.impl;

import com.thought_store.dto.MessageDto;
import com.thought_store.dto.UpdateMessageDto;
import com.thought_store.entity.Message;
import com.thought_store.exception.ResourceNotFoundException;
import com.thought_store.mapper.MessageMapper;
import com.thought_store.repository.MessageRepo;
import com.thought_store.service.MessageService;
import com.thought_store.util.MessageUtil;
import com.thought_store.util.UniqueIdGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepo;
    private final MessageMapper mapper;
    private final UniqueIdGenerator generator;
    private final MessageUtil messageUtil;

    @Override
    public Long storeMessage(MessageDto messageDto) {
        Message message = mapper.mapToMessage(messageDto, new Message());
        message.setId(generator.generate10DigitId());
        return messageRepo.save(message).getId();
    }

    @Override
    public MessageDto getMessageByChatId(Long chatId) {
        Message message = messageRepo.findByChatId(chatId);
        if (message == null) throw new ResourceNotFoundException("Message", "chatId", String.valueOf(chatId));
        return mapper.mapToMessageDto(message, new MessageDto());
    }

    @Override
    public MessageDto getMessageByMessageId(Long messageId) {
        if (messageId == null) throw new IllegalArgumentException("Message ID cannot be null");
        Optional<Message> optionalMessage = messageRepo.findById(messageId);
        return optionalMessage.map(message -> mapper.mapToMessageDto(message, new MessageDto())).orElse(null);
    }

    @Override
    public MessageDto getMessagesByChatIdAndMessageId(Long chatId, Long messageId) {
        Message message = messageRepo.findByIdAndChatId(messageId, chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "messageId and chatId", String.valueOf(messageId)+" and "+String.valueOf(chatId)));
        return mapper.mapToMessageDto(message, new MessageDto());

    }

    @Override
    @Transactional
    public void deleteMessageByChatId(Long chatId) {
        Message messages = messageRepo.findByChatId(chatId);
        if(messages == null)
            throw new ResourceNotFoundException("Message", "chatId", String.valueOf(chatId));
        messageRepo.deleteAllByChatId(chatId);
    }

    @Override
    @Transactional
    public void deleteMessageByMessageAndChatId(Long messageId, Long chatId) {
        Message message = messageRepo.findByIdAndChatId(messageId, chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "messageId and chatId", String.valueOf(messageId)+" and "+String.valueOf(chatId)));
        messageRepo.delete(message);
    }

    @Override
    @Transactional
    public void deleteMessageByMessageId(Long messageId) {
        Message message = messageRepo.findById(messageId).
                orElseThrow(() -> new ResourceNotFoundException("Message", "id", String.valueOf(messageId)));
       messageRepo.delete(message);
    }

    @Override
    public void updateMessageById(Long messageId, UpdateMessageDto messageDto) {
        Message message = messageRepo.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "id", String.valueOf(messageId)));
        message = mapper.mapToMessage(messageDto, message);
        messageRepo.save(message);
    }

    @Override
    public void updateMessageByChatId(Long chatId, UpdateMessageDto messageDto) {
        Message message = messageRepo.findByChatId(chatId);
        if(message == null)
            throw new ResourceNotFoundException("Message", "chatId", String.valueOf(chatId));
        message = mapper.mapToMessage(messageDto, message);
        messageRepo.save(message);
    }

    @Override
    public void updateMessageByChatIdAndMessageId(Long chatId,Long messageId, UpdateMessageDto messageDto) {
        Message message = messageRepo.findByIdAndChatId(messageId, chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "messageId and chatId", String.valueOf(messageId)+" and "+String.valueOf(chatId)));
        if(message == null)
            throw new ResourceNotFoundException("Message", "chatId", String.valueOf(chatId));
        message = mapper.mapToMessage(messageDto, message);
        messageRepo.save(message);
    }
}
