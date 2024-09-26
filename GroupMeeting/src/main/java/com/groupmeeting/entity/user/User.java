package com.groupmeeting.entity.user;

import com.groupmeeting.global.enums.Role;
import com.groupmeeting.global.enums.SocialProvider;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "social_provider_id", nullable = false)
    private String socialProviderId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "last_launch_at")
    private LocalDateTime lastLaunchAt;

    @Column(name = "badge_count")
    private int badgeCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_provider", nullable = false, length = 30)
    private SocialProvider socialProvider;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10)
    private Role role;

    @Builder
    public User(
            SocialProvider socialProvider,
            String socialProviderId,
            String nickname,
            String profileImg,
            String deviceToken,
            LocalDateTime lastLaunchAt,
            int badgeCount
    ) {
        this.socialProvider = socialProvider;
        this.socialProviderId = socialProviderId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.deviceToken = deviceToken;
        this.lastLaunchAt = lastLaunchAt;
        this.badgeCount = badgeCount;
        this.role = Role.USER;
    }
}
