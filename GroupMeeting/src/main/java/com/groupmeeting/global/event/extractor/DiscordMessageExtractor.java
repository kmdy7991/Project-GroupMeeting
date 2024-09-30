package com.groupmeeting.global.event.extractor;

import com.groupmeeting.global.event.dto.DiscordMessage;

@FunctionalInterface
public interface DiscordMessageExtractor<T> extends MessageExtractor<T, DiscordMessage> {
    @Override
    DiscordMessage extract(T message);
}