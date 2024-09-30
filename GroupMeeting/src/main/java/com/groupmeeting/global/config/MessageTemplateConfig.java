package com.groupmeeting.global.config;

import com.groupmeeting.global.client.DiscordMessageClient;
import com.groupmeeting.global.event.dto.DiscordMessage;
import com.groupmeeting.global.event.dto.ErrorAlertMessage;
import com.groupmeeting.global.event.extractor.ErrorAlertDiscordMessageExtractor;
import com.groupmeeting.global.event.extractor.MessageExtractor;
import com.groupmeeting.global.event.sender.DiscordMessageSender;
import com.groupmeeting.global.event.sender.MessageSender;
import com.groupmeeting.global.event.template.MessageTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageTemplateConfig {
    private final DiscordMessageClient client;

    public MessageTemplateConfig(DiscordMessageClient client) {
        this.client = client;
    }

    @Bean
    public MessageTemplate messageTemplate() {
        return new MessageTemplate();
    }

    @Bean
    public MessageExtractor<ErrorAlertMessage, DiscordMessage> errorAlertMessageExtractor() {
        return new ErrorAlertDiscordMessageExtractor();
    }

    @Bean
    public MessageSender<DiscordMessage> discordMessageSender() {
        return new DiscordMessageSender(client);
    }
}
