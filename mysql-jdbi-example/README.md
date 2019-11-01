# JDBI (MySQL) example
---

How to start the application
---

1. Use the file schema.sql to create the database
1. In the mysql-jdbi-example folder run `mvn install -DskipTests` to build the application
1. Start application with `java -jar target/mysql-jdbi-example-1.0-SNAPSHOT.jar server config.yml`

Testing
---

Use the follwing curl command to add a new user: `curl -d '{"firstName":"diego", "lastName":"domingos", "phone":"+555555"}' -H "Content-Type: application/json" -H "Authorization: Basic d3N1c2VyOndzcGFzc3dvcmQ=" -X POST http://localhost:8080/contacts`. You can also do GET, POST and DELETE
p.s: d3N1c2VyOndzcGFzc3dvcmQ is the base64 encoding of `wsuser:wspassword` which were the credentials created in schema.sql
