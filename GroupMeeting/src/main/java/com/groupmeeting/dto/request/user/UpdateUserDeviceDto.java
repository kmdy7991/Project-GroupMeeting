package com.groupmeeting.dto.request.user;

import jakarta.validation.constraints.NotEmpty;

public record UpdateUserDeviceDto(
        @NotEmpty String deviceToken
) {
}
