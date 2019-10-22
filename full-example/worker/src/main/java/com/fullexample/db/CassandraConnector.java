package com.fullexample.db;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class CassandraConnector implements DbConnector {
    private Cluster cluster;
    private Session session;
    private final String node;
    private final Integer port;

    @Inject
    public CassandraConnector(@Named("DatabaseHost") String node, @Named("DatabasePort") Integer port) {
        this.node = node;
        this.port = port;
    }

    @Override
    public void connect() {
        Cluster.Builder builder = Cluster.builder().addContactPoint(node);
        if (port != null) {
            builder.withPort(port);
        }
        cluster = builder.build();
        session = cluster.connect();
    }

    @Override
    public void execute(String query) {
        session.execute(query);
        System.out.println("Executing query: " + query);
    }

    @Override
    public void close() {
        session.close();
        cluster.close();
    }
}
