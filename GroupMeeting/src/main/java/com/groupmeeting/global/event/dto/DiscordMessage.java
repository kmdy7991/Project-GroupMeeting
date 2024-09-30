package com.groupmeeting.global.event.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class DiscordMessage implements Message {
    String content;
    List<DiscordMessagePayload> payload;
}

