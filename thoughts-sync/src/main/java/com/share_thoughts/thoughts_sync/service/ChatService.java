package com.share_thoughts.thoughts_sync.service;

import com.share_thoughts.thoughts_sync.dto.*;

import java.util.List;

public interface ChatService {
    Long sendOTOMessage(Long receiverNumber, OneToOneChatDto dto);

    Long sendGroupMessage(Long groupId, OneToOneChatDto dto);

    DeleteResponseDto deleteMessages(List<IDAloneDto> list);

    void editMessage(ChatUpdateRequestDto dto);

    List<ChatResponseDto> getMessagesByOTOId(Long otoId);

    List<ChatResponseDto> getMessagesByGroupId(Long groupId);

    void addUserToTheGroup(Long groupId, Long userId);

    void addMeToTheGroup(Long groupId);

    void updateUserRole(Long groupId, UpdateUserTypeDto dto);
}
