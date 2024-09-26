package com.groupmeeting.user.dto;

import jakarta.validation.constraints.NotEmpty;

public record UpdateUserDeviceDto(
        @NotEmpty String deviceToken
) {
}
