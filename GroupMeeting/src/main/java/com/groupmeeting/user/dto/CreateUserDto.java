package com.groupmeeting.user.dto;

import com.groupmeeting.user.type.SocialProvider;

public record CreateUserDto(SocialProvider provider, String socialProviderId) {
}
