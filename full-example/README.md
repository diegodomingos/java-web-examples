# Full example
---

This example aims to combine all technologies from the other projects: guice, dropwizard, rabbitmq, cassandra and lombok. It consist of two applications: the first one is a dropwizard REST API that receives a POST message to add a user and passes that user as a serialized JSON message using a RabbitMQ queue. The second one is a worker, that reads from that message queue and saves the user in a cassandra database. Guice was used for dependency injection and lombok in the model class (user) as an example.

How to start the application
---

1. In the rest-api folder run `mvn install` to build the first application
1. Start application with `java -jar target/rest-api-1.0-SNAPSHOT.jar`
1. In the worker folder run `mvn install` to build the second application
1. Start application with `java -jar target/worker-1.0-SNAPSHOT.jar`

Testing
---

Use the follwing curl command to add a new user: `curl -d '{"firstName":"diego", "lastName":"domingos"}' -H "Content-Type: application/json" -X POST http://localhost:8080/users`

You can also check if the RabbitMQ queue is ready using the health check `http://localhost:8081/healthcheck`
