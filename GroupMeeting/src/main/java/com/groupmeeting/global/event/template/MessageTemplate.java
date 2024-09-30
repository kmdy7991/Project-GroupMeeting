package com.groupmeeting.global.event.template;

import com.groupmeeting.global.event.extractor.MessageExtractor;
import com.groupmeeting.global.event.sender.MessageSender;

public class MessageTemplate {
    <T, U> void sendMessage(
            T message,
            MessageExtractor<T, U> extractor,
            MessageSender<U> sender
    ) {
        U extractMessage = extractor.extract(message);
        sender.send(extractMessage);
    }
}