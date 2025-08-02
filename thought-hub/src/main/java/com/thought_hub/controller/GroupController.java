package com.thought_hub.controller;

import com.thought_hub.dto.CommonResponseDto;
import com.thought_hub.dto.GroupDto;
import com.thought_hub.dto.GroupCreateResponseDto;
import com.thought_hub.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createGroup(@RequestBody GroupDto dto) {
        Long id = groupService.createGroup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GroupCreateResponseDto(id));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<GroupDto> getGroup(@RequestParam Long id) {
        GroupDto dto = groupService.getGroup(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateGroup(@RequestBody GroupDto dto) {
        groupService.updateGroup(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDto(HttpStatus.OK.toString(), "Group updated successfully"));
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<?> deleteGroup(@RequestParam Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDto(HttpStatus.OK.toString(), "Group deleted successfully"));
    }
}

