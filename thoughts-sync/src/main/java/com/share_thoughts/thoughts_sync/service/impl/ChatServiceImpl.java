package com.share_thoughts.thoughts_sync.service.impl;

import com.share_thoughts.thoughts_sync.client.GroupClient;
import com.share_thoughts.thoughts_sync.client.MessageClient;
import com.share_thoughts.thoughts_sync.client.UserManagerClient;
import com.share_thoughts.thoughts_sync.constant.ChatType;
import com.share_thoughts.thoughts_sync.constant.GroupRole;
import com.share_thoughts.thoughts_sync.dto.*;
import com.share_thoughts.thoughts_sync.entity.Chat;
import com.share_thoughts.thoughts_sync.entity.OneToOne;
import com.share_thoughts.thoughts_sync.entity.UserGroup;
import com.share_thoughts.thoughts_sync.exception.ResourceNotFoundException;
import com.share_thoughts.thoughts_sync.repository.ChatRepo;
import com.share_thoughts.thoughts_sync.repository.OTORepo;
import com.share_thoughts.thoughts_sync.repository.UserGroupRepo;
import com.share_thoughts.thoughts_sync.service.ChatService;
import com.share_thoughts.thoughts_sync.util.UniqueIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageClient messageClient;
    private final GroupClient groupClient;
    private final UserManagerClient userManagerClient;
    private final ChatRepo chatRepo;
    private final OTORepo otoRepo;
    private final UserGroupRepo groupRepo;
    private final UniqueIdGenerator generator;

    @Override
    public Long sendOTOMessage(Long receiverId, OneToOneChatDto dto) {
        UserManagerDto sender = userManagerClient.getMe();
        UserManagerDto receiver = userManagerClient.getUserByKeycloakIdOrUserId(null, receiverId.toString());
        if (receiver == null)
            throw new ResourceNotFoundException("User", "receiver_id", receiverId.toString());
        OneToOne oto = otoRepo.findByUserId1AndUserId2(sender.getId(), receiver.getId())
                .orElseGet(() ->
                        otoRepo.findByUserId1AndUserId2(receiver.getId(), sender.getId())
                                .orElseGet(() -> {
                                    OneToOne newOto = OneToOne.builder()
                                            .id(generator.generate10DigitId())
                                            .userId1(sender.getId())
                                            .userId2(receiver.getId())
                                            .build();
                                    return otoRepo.save(newOto);
                                })
                );


        Chat chat = Chat.builder()
                .chatType(ChatType.ONE_TO_ONE)
                .otoId(oto.getId())
                .id(generator.generate10DigitId())
                .build();

        MessageDto message = MessageDto.builder()
                .chatId(chat.getId())
                .contentType(dto.getContentType())
                .content(dto.getContent())
                .build();

        messageClient.storeMessage(message);
        return chatRepo.save(chat).getId();
    }

    @Override
    public Long sendGroupMessage(Long groupId, OneToOneChatDto dto) {
        UserGroup userGroup = groupRepo.findByGroupIdAndUserId(groupId, userManagerClient.getMe().getId())
                .orElseThrow(() -> new ResourceNotFoundException("UserGroup", "groupId and userId", groupId.toString() + " and " + userManagerClient.getMe().getId().toString()));
        Chat chat = Chat.builder()
                .chatType(ChatType.GROUP)
                .groupId(groupId)
                .id(generator.generate10DigitId())
                .build();

        MessageDto message = MessageDto.builder()
                .chatId(chat.getId())
                .contentType(dto.getContentType())
                .content(dto.getContent())
                .build();

        messageClient.storeMessage(message);
        return chatRepo.save(chat).getId();

    }

    @Override
    public DeleteResponseDto deleteMessages(List<IDAloneDto> list) {
        DeleteResponseDto response = new DeleteResponseDto();
        Long currentUserId = userManagerClient.getMe().getId();
        for(IDAloneDto dto : list) {
            try{
                Chat chat = chatRepo.findByIdAndSenderId(dto.getId(), currentUserId)
                                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id and senderId", dto.getId().toString() + " and " + currentUserId.toString()));

                messageClient.deleteMessage(null, chat.getId());
                chatRepo.deleteById(chat.getId());
            }
            catch(Exception e) {
                response.getErrors().add(e.getMessage());
            }
        }
        return response;
    }

    @Override
    public void editMessage(ChatUpdateRequestDto dto) {
        Long currentUserId = userManagerClient.getMe().getId();
        Chat chat = chatRepo.findByIdAndSenderId(dto.getChatId(), currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id and senderId", dto.getChatId().toString() + " and " + currentUserId.toString()));
        UpdateMessageRequestDto messageDto = new UpdateMessageRequestDto(dto.getUpdatedContent());
        messageClient.updateMessage(chat.getId(), null,messageDto);
    }

    @Override
    public List<ChatResponseDto> getMessagesByOTOId(Long otoId) {
        OneToOne oneToOne = otoRepo.findById(otoId)
                .orElseThrow(() -> new ResourceNotFoundException("OneToOne", "id", otoId.toString()));
        List<Chat> chats = chatRepo.findAllByOtoId(otoId)
                .orElseGet(List::of);
        List<ChatResponseDto> response = new ArrayList<>();
        for(Chat chat : chats) {
            MessageDto messageDto = messageClient.getMessage(chat.getId(), null);
            ChatResponseDto chatResponseDto = ChatResponseDto.builder()
                    .chatId(chat.getId())
                    .content(messageDto.getContent())
                    .contentType(messageDto.getContentType())
                    .messageId(messageDto.getId())
                    .senderId(chat.getSenderId())
                    .sentAt(chat.getSentAt())
                    .editedAt(chat.getEditedAt())
                    .build();
            response.add(chatResponseDto);
        }
        return response;
    }

    @Override
    public List<ChatResponseDto> getMessagesByGroupId(Long groupId) {
        List<UserGroup> userGroup = groupRepo.findAllByGroupId(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("UserGroup", "id", groupId.toString()));
        List<Chat> chats = chatRepo.findAllByGroupId(groupId)
                .orElseGet(List::of);
        List<ChatResponseDto> response = new ArrayList<>();
        for(Chat chat : chats) {
            MessageDto messageDto = messageClient.getMessage(chat.getId(), null);
            ChatResponseDto chatResponseDto = ChatResponseDto.builder()
                    .chatId(chat.getId())
                    .content(messageDto.getContent())
                    .contentType(messageDto.getContentType())
                    .messageId(messageDto.getId())
                    .senderId(chat.getSenderId())
                    .sentAt(chat.getSentAt())
                    .editedAt(chat.getEditedAt())
                    .build();
            response.add(chatResponseDto);
        }
        return response;
    }

    @Override
    public void addUserToTheGroup(Long groupId, Long userId) {
        GroupDto group = groupClient.getGroupInfo(groupId);
        if (group == null)
            throw new ResourceNotFoundException("Group", "id", groupId.toString());
        UserManagerDto user = userManagerClient.getUserByKeycloakIdOrUserId(null,userId.toString());
        if (user == null)
            throw new ResourceNotFoundException("User", "id", userId.toString());
        UserGroup userGroup = UserGroup.builder()
                                .groupId(group.getId())
                                .userId(user.getId())
                                .build();
        Long createdBy = groupClient.getGroupInfo(groupId).getCreatedBy();
        if (createdBy.longValue() == userId.longValue())
            userGroup.setGroupRole(GroupRole.ADMIN);
        else
            userGroup.setGroupRole(GroupRole.MEMBER);
        groupRepo.save(userGroup);
    }

    @Override
    public void addMeToTheGroup(Long groupId) {
        GroupDto group = groupClient.getGroupInfo(groupId);
        if (group == null)
            throw new ResourceNotFoundException("Group", "id", groupId.toString());
        UserManagerDto user = userManagerClient.getMe();
        if (user == null)
            throw new ResourceNotFoundException("User", "id", "me");
        UserGroup userGroup = UserGroup.builder()
                .groupId(group.getId())
                .userId(user.getId())
                .build();
        Long createdBy = groupClient.getGroupInfo(groupId).getCreatedBy();
        if (createdBy.longValue() == user.getId())
            userGroup.setGroupRole(GroupRole.ADMIN);
        else
            userGroup.setGroupRole(GroupRole.MEMBER);
        groupRepo.save(userGroup);
    }

    @Override
    public void updateUserRole(Long groupId, UpdateUserTypeDto dto) {
        UserManagerDto currentUser = userManagerClient.getMe();

        UserGroup currentUserGroup = groupRepo.findByGroupIdAndUserId(groupId, currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("UserGroup", "groupId and userId", groupId + " and " + currentUser.getId()));

        UserGroup targetUserGroup = groupRepo.findByGroupIdAndUserId(groupId, dto.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("UserGroup", "groupId and userId", groupId + " and " + dto.getUser_id()));

        if (currentUserGroup.getGroupRole() != GroupRole.ADMIN) {
            throw new RuntimeException("Only ADMIN users can change roles. Your role: " + currentUserGroup.getGroupRole());
        }

        if (targetUserGroup.getGroupRole() == GroupRole.ADMIN && dto.getRole() != GroupRole.ADMIN) {
            GroupDto group = groupClient.getGroupInfo(groupId);

            if (!group.getCreatedBy().equals(currentUser.getId())) {
                throw new RuntimeException("Only the group creator can change another ADMIN’s role.");
            }
        }

        targetUserGroup.setGroupRole(dto.getRole());
        groupRepo.save(targetUserGroup);
    }



}

