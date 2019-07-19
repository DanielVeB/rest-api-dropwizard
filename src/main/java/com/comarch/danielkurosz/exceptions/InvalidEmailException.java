package com.comarch.danielkurosz.exceptions;

import javax.ws.rs.core.Response;

public class InvalidEmailException extends AppException {

    public InvalidEmailException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "Invalid email", "Please check your email and try again ", "https://en.wikipedia.org/wiki/Email_address");
    }


}
