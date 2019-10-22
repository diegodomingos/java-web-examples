package com.fullexample;

import com.fullexample.resources.MessageQueueHealthCheck;
import com.fullexample.resources.UsersResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class RestApiApplication extends Application<RestApiConfiguration> {

    public static void main(final String[] args) throws Exception {
        new RestApiApplication().run(args);
    }

    public void run(RestApiConfiguration restApiConfiguration, Environment environment) throws Exception {
        Injector injector = Guice.createInjector(new GuiceModule());
        final UsersResource usersResource = injector.getInstance(UsersResource.class);
        final MessageQueueHealthCheck healthCheck = injector.getInstance(MessageQueueHealthCheck.class);
        environment.jersey().register(usersResource);
        environment.healthChecks().register("queueHealthCheck", healthCheck);

    }
}
