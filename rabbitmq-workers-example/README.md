# RabbitMQ example 

How to run the example
---

1. Install `rabbitmq-server` as per instructions in `https://www.rabbitmq.com/install-debian.html`
1. Build the project with `mvn install`
1. Start two or more workers (one in each terminal) with `java -cp target/rabbitmq-workers-example-1.0-SNAPSHOT.jar Worker`
1. Send messages (producer) with `java -cp target/rabbitmq-workers-example-1.0-SNAPSHOT.jar NewTask First Message`, `java -cp target/rabbitmq-workers-example-1.0-SNAPSHOT.jar NewTask First Message` and so on. You'll notice each message gets handled by a different worker
1. You can run a publish/subscriber example with the same steps but using ReceiveLogs (instead of Worker) and EmitLog (instead of NewTask). You'll notice this time all workers get the message
