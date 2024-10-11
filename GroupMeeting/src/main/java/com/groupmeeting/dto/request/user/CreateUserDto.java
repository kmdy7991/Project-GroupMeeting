package com.groupmeeting.dto.request.user;

import com.groupmeeting.global.enums.SocialProvider;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(
        @NotEmpty @NotNull String deviceToken,
        @NotEmpty @NotNull String socialProviderId,
        @NotEmpty @NotNull SocialProvider provider
) {
}
