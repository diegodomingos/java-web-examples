package com.fullexample.db;

import com.fullexample.model.User;
import com.google.inject.Inject;

public class UserRepository {
    public static final String KEYSPACE_NAME = "fullexample";
    public static final String TABLE_NAME = "users";
    private final DbConnector connector;

    @Inject
    public UserRepository(DbConnector connector) {
        this.connector = connector;
        connector.connect();
    }

    public void createKeyspaceSystem() {
        StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
                .append(KEYSPACE_NAME).append(" WITH replication = {")
                .append("'class':'SimpleStrategy','replication_factor':1};");
        final String query = sb.toString();
        connector.execute(query);
    }

    public void createTableUsers() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(KEYSPACE_NAME).append(".")
                .append(TABLE_NAME).append("(")
                .append("id uuid PRIMARY KEY, ")
                .append("firstName text, ")
                .append("lastName text);");
        final String query = sb.toString();
        connector.execute(query);
    }

    public void insertUser(User user) {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(KEYSPACE_NAME).append(".")
                .append(TABLE_NAME)
                .append(" (id, firstName, lastName) VALUES (")
                .append(user.getId()).append(", '")
                .append(user.getFirstName()).append("', '")
                .append(user.getLastName()).append("');");
        final String query = sb.toString();
        connector.execute(query);
    }
}
