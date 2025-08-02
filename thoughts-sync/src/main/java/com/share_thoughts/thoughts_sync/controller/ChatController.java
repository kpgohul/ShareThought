package com.share_thoughts.thoughts_sync.controller;

import com.share_thoughts.thoughts_sync.dto.*;
import com.share_thoughts.thoughts_sync.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/chats",consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService service;

    @PostMapping("/oto")
    public ResponseEntity<?> sendMessage(@RequestParam Long receiverId,@RequestBody OneToOneChatDto dto)
    {
        log.info("Request recieved: {} for ONE TO ONE sendMessage ", dto);
        Long id = service.sendOTOMessage(receiverId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new IDAloneDto(id));
    }

    @PostMapping("/group")
    public ResponseEntity<?> sendGroupMessage(@RequestParam Long groupId,@RequestBody OneToOneChatDto dto)
    {
        log.info("Request recieved: {} for GROUP sendMessage", dto);
        Long id = service.sendGroupMessage(groupId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new IDAloneDto(id));
    }

    @DeleteMapping("/chat")
    public ResponseEntity<?> deleteMessage(@RequestBody List<IDAloneDto> list)
    {
        log.info("Request recieved: {} for deleteMessage", list);
        DeleteResponseDto res = service.deleteMessages(list);
        if (res.getErrors().isEmpty())
            return ResponseEntity.status(HttpStatus.OK).build();
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @PutMapping("/chat")
    public ResponseEntity<?> updateMessage(@RequestBody ChatUpdateRequestDto dto)
    {
        log.info("Request recieved: {} for updateMessage", dto);
        service.editMessage(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<ChatResponseDto>> getMessagesByOTOIdOrGroupId(
            @RequestParam(required = false) Long oto_id,
            @RequestParam(required = false) Long group_id
    )
    {
        log.info("Request received for get messages - otoId ::{} and groupId ::{}",oto_id,group_id);
        if (oto_id != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(service.getMessagesByOTOId(oto_id));
        }
        else if (group_id != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(service.getMessagesByGroupId(group_id));
        }
        else{
            throw new IllegalArgumentException("Either oto_id or group_id must be provided");
        }
    }

    @PostMapping("/add-user-to-group")
    public ResponseEntity<?> addUserToTheGroup(
            @RequestParam Long groupId,
            @RequestParam(required = false) Long userId
    )
    {
        log.info("Request received for add user to the group - groupId ::{} and userId ::{}",groupId,userId);
        if (userId != null) {
            service.addUserToTheGroup(groupId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            service.addMeToTheGroup(groupId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    }

    @PostMapping("/update-user-role")
    public ResponseEntity<?> updateUserRole(@RequestParam Long groupId,@RequestBody UpdateUserTypeDto dto)
    {
        service.updateUserRole(groupId, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }




}
