package com.comarch.danielkurosz.exceptions;

public class InvalidEmailError extends Exception {

    public InvalidEmailError() {
        super();
    }

    @Override
    public String getMessage() {
        return "Invalid email";
    }
}
