package com.fullexample;

import com.datastax.driver.core.utils.UUIDs;
import com.fullexample.db.UserRepository;
import com.fullexample.model.User;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.codehaus.jackson.map.ObjectMapper;

public class Worker {
    private final static String QUEUE_NAME = "pipe";
    private final UserRepository repository;

    @Inject
    public Worker(UserRepository repository) {
        this.repository = repository;
        this.repository.createKeyspaceSystem();
        this.repository.createTableUsers();
    }

    public static void main(String[] argv) throws Exception {
        Injector injector = Guice.createInjector(new GuiceModule());
        Worker worker = injector.getInstance(Worker.class);
        worker.run();
    }

    public void run() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Received JSON '" + message + "'");
            ObjectMapper mapper = new ObjectMapper();
            User parsedUser = mapper.readValue(message, User.class);
            parsedUser.setId(UUIDs.timeBased());
            saveUser(parsedUser);
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        System.out.println("Waiting for messages. To exit press CTRL+C");
    }

    private void saveUser(User user)  {
        System.out.println("Saving user " + user.getLastName() + ", " + user.getFirstName() + "(" + user.getId() + ")");
        repository.insertUser(user);
    }
}
