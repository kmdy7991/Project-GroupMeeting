package com.groupmeeting.user.entity;

import com.groupmeeting.user.type.DeviceType;
import com.groupmeeting.user.type.SocialProvider;
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
    private Long id;

    @Column(nullable = false)
    private String socialProviderId;

    private String nickname;

    private String profileImg;

    private String deviceToken;

    private String appleRefreshToken;

    private LocalDateTime lastLaunchAt;

    private int badgeCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialProvider socialProvider;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Builder
    public User(
            SocialProvider socialProvider,
            String socialProviderId,
            String nickname,
            String profileImg,
            String deviceToken,
            DeviceType deviceType,
            String appleRefreshToken,
            LocalDateTime lastLaunchAt,
            int badgeCount
    ) {
        this.socialProvider = socialProvider;
        this.socialProviderId = socialProviderId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.deviceToken = deviceToken;
        this.deviceType = deviceType;
        this.appleRefreshToken = appleRefreshToken;
        this.lastLaunchAt = lastLaunchAt;
        this.badgeCount = badgeCount;
    }
}
