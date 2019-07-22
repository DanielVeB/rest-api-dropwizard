package com.comarch.danielkurosz.auth;

import java.security.Principal;

public class AuthUser implements Principal {


    private String login;
    private String password;

    public AuthUser(String login) {
        this.login = login;
    }

    public AuthUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }











    @Override
    public String getName() {
        return null;
    }
}
