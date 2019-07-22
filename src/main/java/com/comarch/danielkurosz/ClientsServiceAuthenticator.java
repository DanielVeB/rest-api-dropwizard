package com.comarch.danielkurosz;

import com.comarch.danielkurosz.auth.AuthUser;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class ClientsServiceAuthenticator implements Authenticator<BasicCredentials, AuthUser> {

    private String login;
    private String password;

    ClientsServiceAuthenticator(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Optional<AuthUser> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if (credentials.getUsername().equals(this.login) && credentials.getPassword().equals(this.password)) {
            return Optional.of(new AuthUser(credentials.getUsername()));
        }
        return Optional.absent();
    }
}
