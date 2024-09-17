package com.groupmeeting.user.dto;

import com.groupmeeting.user.type.DeviceType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateUserDeviceDto(
        @NotEmpty String deviceToken,
        @NotNull DeviceType deviceType
) {
}
