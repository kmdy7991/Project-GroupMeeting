package com.groupmeeting.global.event.extractor;

import com.groupmeeting.global.event.dto.DiscordMessage;
import com.groupmeeting.global.event.dto.ErrorAlertMessage;

public class ErrorAlertDiscordMessageExtractor implements DiscordMessageExtractor<ErrorAlertMessage> {
    @Override
    public DiscordMessage extract(ErrorAlertMessage message) {
        return null;
    }
}
