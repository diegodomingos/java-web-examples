package com.example.auth;

import com.example.dao.UserDAO;
import com.example.model.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

public class PhonebookAuthenticator implements Authenticator<BasicCredentials, User> {
    private final UserDAO userDAO;

    public PhonebookAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        if (userDAO.getUser(basicCredentials.getUsername(), basicCredentials.getPassword()) == 1) {
            return Optional.of(new User(basicCredentials.getUsername()));
        }
        return Optional.empty();
    }
}
