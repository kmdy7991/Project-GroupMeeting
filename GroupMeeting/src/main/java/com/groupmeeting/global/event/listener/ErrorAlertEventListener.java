package com.groupmeeting.global.event.listener;

import com.groupmeeting.global.annotation.event.ApplicationEventListener;
import com.groupmeeting.global.event.dto.DiscordMessage;
import com.groupmeeting.global.event.dto.ErrorAlertMessage;
import com.groupmeeting.global.event.extractor.ErrorAlertDiscordMessageExtractor;
import com.groupmeeting.global.event.extractor.MessageExtractor;
import com.groupmeeting.global.event.sender.DiscordMessageSender;
import com.groupmeeting.global.event.sender.MessageSender;
import com.groupmeeting.global.event.template.MessageTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

@ApplicationEventListener
public class ErrorAlertEventListener {
    private final MessageTemplate messageTemplate;
    private final MessageExtractor<ErrorAlertMessage, DiscordMessage> messageExtractor;
    private final MessageSender<DiscordMessage> messageSender;

    public ErrorAlertEventListener(
            MessageTemplate messageTemplate,
            @Qualifier("errorAlertMessageExtractor") MessageExtractor<ErrorAlertMessage, DiscordMessage> messageExtractor,
            @Qualifier("discordMessageSender") MessageSender<DiscordMessage> messageSender) {
        this.messageTemplate = messageTemplate;
        this.messageExtractor = messageExtractor;
        this.messageSender = messageSender;
    }
}
