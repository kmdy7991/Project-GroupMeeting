package com.groupmeeting.global.event.dto;

import lombok.Builder;

@Builder
public record DiscordMessagePayload(
        String title,
        String description
) {
}
