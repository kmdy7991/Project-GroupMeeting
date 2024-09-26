package com.groupmeeting.user.dto;

import com.groupmeeting.global.enums.SocialProvider;

public record CreateUserDto(SocialProvider provider, String socialProviderId) {
}
