package com.groupmeeting.global.event.sender;

import com.groupmeeting.global.client.DiscordMessageClient;
import com.groupmeeting.global.event.dto.DiscordMessage;

public class DiscordMessageSender implements MessageSender<DiscordMessage> {
    private final DiscordMessageClient client;

    public DiscordMessageSender(DiscordMessageClient client) {
        this.client = client;
    }

    @Override
    public void send(DiscordMessage message) {
        client.sendMessage(message);
    }
}
