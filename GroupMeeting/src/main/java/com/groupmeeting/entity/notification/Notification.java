package com.groupmeeting.entity.notification;

import com.groupmeeting.entity.common.BaseTimeEntity;
import com.groupmeeting.entity.user.User;
import com.groupmeeting.global.enums.ActionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "message", length = 500)
    private String message;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "action_type", length = 30)
    private ActionType actionType = ActionType.DEFAULT;

//    @Type(JsonType.class)
//    @Column(columnDefinition = "json")
//    private NotificationBody dataBody;

    @Column(name = "send_at")
    private LocalDateTime sendAt;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Builder.Default
    @Column(name = "expired_at")
    private LocalDateTime expiredAt = LocalDateTime.parse("2999-12-31T00:00:00");

}
