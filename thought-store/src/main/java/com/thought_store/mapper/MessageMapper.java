package com.thought_store.mapper;

import com.thought_store.dto.MessageDto;
import com.thought_store.dto.UpdateMessageDto;
import com.thought_store.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public Message mapToMessage(MessageDto messageDto, Message message) {
        message.setContentType(messageDto.getContentType());
        message.setContent(messageDto.getContent());
        message.setChatId(messageDto.getChatId());
        message.setSentAt(messageDto.getSentAt());
        message.setEditedAt(messageDto.getEditedAt());
        return message;
    }

    public MessageDto mapToMessageDto(Message message, MessageDto messageDto) {
        messageDto.setId(message.getId());
        messageDto.setContentType(message.getContentType());
        messageDto.setContent(message.getContent());
        messageDto.setChatId(message.getChatId());
        messageDto.setSentAt(message.getSentAt());
        messageDto.setEditedAt(message.getEditedAt());
        return messageDto;
    }

    public Message mapToMessage(UpdateMessageDto updateMessageDto, Message message) {
        message.setContent(updateMessageDto.getContent());
        return message;
    }
}
