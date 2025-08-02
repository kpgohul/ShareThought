package com.share_thoughts.userprofile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString @Builder
@EntityListeners(AuditingEntityListener.class)
public class UserProfile {

    @Id
    private Long id;
    @Column(unique = true, nullable = false)
    private String KeyCloakId;
    @Column(name = "phone_number")
    private Long phoneNumber;
    @Column(name = "username")
    private String username;
    @Column(name = "about")
    private String about;
    @Column(name = "status")
    private String status;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
