package com.comarch.danielkurosz.service;

public class InvalidEmailError extends Exception {

    InvalidEmailError() {
        super();
    }

    @Override
    public String getMessage() {
        return "Invalid email";
    }
}
