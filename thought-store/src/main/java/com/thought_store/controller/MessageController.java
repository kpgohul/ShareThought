package com.thought_store.controller;


import com.thought_store.dto.CreateMessageDto;
import com.thought_store.dto.MessageDto;
import com.thought_store.dto.UpdateMessageDto;
import com.thought_store.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<?> storeMessage(@RequestBody MessageDto messageDto) {
        log.info("Request recieved: {} for storeMessage", messageDto);
        Long id = messageService.storeMessage(messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateMessageDto(id));
    }

    @GetMapping
    public ResponseEntity<MessageDto> getMessagesByChatOrMessageId(
            @RequestParam(required = false) Long chat_id,
            @RequestParam(required = false) Long message_id
    ) {
        log.info("Request received for get messages - chatId ::{} and messageId ::{}",chat_id,message_id);
        if (chat_id != null && message_id != null) {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessagesByChatIdAndMessageId(chat_id, message_id));
        }
        else if (chat_id != null) {
            MessageDto messages = messageService.getMessageByChatId(chat_id);
            return ResponseEntity.status(HttpStatus.OK).body(messages);
        }
        else if (message_id != null) {
            MessageDto messages = messageService.getMessageByMessageId(message_id);
            return ResponseEntity.status(HttpStatus.OK).body(messages);
        }
        else{
            throw new IllegalArgumentException("Either phoneNumber or userName or both must be provided");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateMessage(
            @RequestParam(required = false) Long message_id,
            @RequestParam(required = false) Long chat_id,
            @RequestBody UpdateMessageDto messageDto) {
        log.info("Request received for update message - chatId ::{} and messageId ::{}",chat_id,message_id);
        if(message_id != null && chat_id != null)
        {
            messageService.updateMessageByChatIdAndMessageId(chat_id,message_id, messageDto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        if(message_id != null)
        {
            messageService.updateMessageById(message_id, messageDto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else if(chat_id != null)
        {
            messageService.updateMessageByChatId(chat_id, messageDto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new IllegalArgumentException("Either messageId or chatId  must be provided");
    }


    @DeleteMapping
    public ResponseEntity<?> deleteMessageByChatOrMessageId(
            @RequestParam(required = false) Long chat_id,
            @RequestParam(required = false) Long message_id
    ) {
        log.info("Request received for delete message - chatId ::{} and messageId ::{}",chat_id,message_id);
        if(message_id != null && chat_id != null){
            messageService.deleteMessageByMessageAndChatId(message_id, chat_id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        if (chat_id != null) {
            messageService.deleteMessageByChatId(chat_id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else if (message_id != null) {
            messageService.deleteMessageByMessageId(message_id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else{
            throw new IllegalArgumentException("Either phoneNumber or userName or both must be provided");
        }
    }


}

