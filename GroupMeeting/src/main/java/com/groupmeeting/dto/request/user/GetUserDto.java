package com.groupmeeting.dto.request.user;

public record GetUserDto (
        Long id,
        String nickname,
        String imageUrl,
        int badgeCount
) {
}
