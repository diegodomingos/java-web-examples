package com.fullexample;


import com.fullexample.message.MessageSender;
import com.fullexample.message.RabbitMQSender;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MessageSender.class).to(RabbitMQSender.class).in(Singleton.class);
    }
}
