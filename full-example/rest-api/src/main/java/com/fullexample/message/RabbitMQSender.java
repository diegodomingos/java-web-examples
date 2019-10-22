package com.fullexample.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQSender implements MessageSender {
    private Channel channel;
    private static final String QUEUE_NAME = "pipe";

    @Override
    public void init() throws Exception {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        final Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    }

    @Override
    public boolean isReady() {
        return channel.isOpen();
    }

    @Override
    public void send(String message) throws Exception {
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("Sent '" + message + "'");
    }
}
