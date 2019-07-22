package com.comarch.danielkurosz.exceptions;

import javax.ws.rs.core.Response;

public class InvalidUUIDException extends AppException {

    public InvalidUUIDException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "Invalid UUID String", "Please add correct ID", "https://en.wikipedia.org/wiki/Universally_unique_identifier");
    }
}
