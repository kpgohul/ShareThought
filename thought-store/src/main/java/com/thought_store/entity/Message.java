package com.thought_store.entity;

import com.thought_store.constant.ContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "message_entity")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "chat_at", nullable = false)
    private Long chatId;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime sentAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime editedAt;
}
