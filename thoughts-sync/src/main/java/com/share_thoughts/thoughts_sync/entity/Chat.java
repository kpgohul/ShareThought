package com.share_thoughts.thoughts_sync.entity;

import com.share_thoughts.thoughts_sync.constant.ChatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_entity")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Chat {

    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private ChatType chatType;
    @Column(name = "oto_id")
    private Long otoId;
    @Column(name = "group_id")
    private Long groupId;
    @CreatedBy
    private Long senderId;
    @CreatedDate
    private LocalDateTime sentAt;
    @LastModifiedDate
    private LocalDateTime editedAt;

}
