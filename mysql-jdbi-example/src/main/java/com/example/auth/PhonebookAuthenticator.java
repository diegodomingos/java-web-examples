package com.example.auth;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.google.inject.Inject;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.skife.jdbi.v2.DBI;

import java.util.Optional;

public class PhonebookAuthenticator implements Authenticator<BasicCredentials, User> {
    private final UserDAO userDAO;

    @Inject
    public PhonebookAuthenticator(DBI jdbi) {
        this.userDAO = jdbi.onDemand(UserDAO.class);;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        if (userDAO.getUser(basicCredentials.getUsername(), basicCredentials.getPassword()) == 1) {
            return Optional.of(new User(basicCredentials.getUsername()));
        }
        return Optional.empty();
    }
}
