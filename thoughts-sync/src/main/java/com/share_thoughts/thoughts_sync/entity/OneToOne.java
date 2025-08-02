package com.share_thoughts.thoughts_sync.entity;

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
@Table(name = "oto_entity")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
@Data
public class OneToOne {

    @Id
    private Long id;
    @Column(name = "user_id1", nullable = false)
    private Long userId1;
    @Column(name = "user_id2", nullable = false)
    private Long userId2;
    @CreatedBy
    private Long createdBy;
    @CreatedDate
    private LocalDateTime startDate;
    @LastModifiedDate
    private LocalDateTime endDate;
}
