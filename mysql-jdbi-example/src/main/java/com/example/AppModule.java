package com.example;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class AppModule extends AbstractModule {

    private final AppConfiguration configuration;
    private final Environment environment;

    public AppModule(AppConfiguration configuration, Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    @Override
    protected void configure() {

    }

    @Singleton
    @Provides
    public DBI getJBDI() {
        final DBIFactory factory = new DBIFactory();
        DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        return jdbi;
    }
}
