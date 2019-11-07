package com.example;

import com.example.auth.PhonebookAuthenticator;
import com.example.dao.ContactDAO;
import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.resources.ContactResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<AppConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("Method App#run() called");

        Injector injector = Guice.createInjector(new AppModule(configuration, environment));
        ContactResource contactResource = injector.getInstance(ContactResource.class);
        PhonebookAuthenticator authenticator = injector.getInstance(PhonebookAuthenticator.class);
        environment.jersey().register(contactResource);
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(authenticator)
                .setRealm("BASIC-AUTH-REAL")
                .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }
}
