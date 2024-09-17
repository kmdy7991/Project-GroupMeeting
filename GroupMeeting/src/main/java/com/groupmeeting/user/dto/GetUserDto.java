package com.groupmeeting.user.dto;

public record GetUserDto (Long id, String nickname, String imageUrl, int badgeCount) {
}
