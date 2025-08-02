package com.share_thoughts.thoughts_sync.entity;

import com.share_thoughts.thoughts_sync.constant.GroupRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@Entity
@Table(name = "user_group_entity")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "user_id")
    private Long userId;
    @Enumerated(EnumType.STRING)
    private GroupRole groupRole;
    @CreatedDate
    private LocalDateTime joinedAt;

}
