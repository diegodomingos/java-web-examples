# HelloWorld

How to create the HelloWorld project
---

1. Run `sudo apt-get install maven` to install mave
1. Run `mvn archetype:generate -DarchetypeGroupId=io.dropwizard.archetypes -DarchetypeArtifactId=java-simple -DarchetypeVersion=2.0.0-rc10` to create the project structure
1. Follow the steps `https://www.dropwizard.io/en/stable/getting-started.html#creating-a-configuration-class`

How to start the HelloWorld application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/dropwizard-example-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080/hello-world`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
