package com.fullexample;

import com.fullexample.db.CassandraConnector;
import com.fullexample.db.DbConnector;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/application.properties"));
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            System.out.println("Error reading properties: " + e.getMessage());
        }
        bind(DbConnector.class).to(CassandraConnector.class);
    }
}
