package com.fullexample.resources;

import com.codahale.metrics.health.HealthCheck;
import com.fullexample.message.MessageSender;
import com.google.inject.Inject;

public class MessageQueueHealthCheck extends HealthCheck {
    private final MessageSender sender;

    @Inject
    public MessageQueueHealthCheck(MessageSender sender) {
        this.sender = sender;
    }

    @Override
    protected Result check() throws Exception {
        if (sender.isReady()) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Message queue is not ready");
        }
    }
}
