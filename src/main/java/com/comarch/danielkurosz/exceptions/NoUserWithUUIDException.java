package com.comarch.danielkurosz.exceptions;

import javax.ws.rs.core.Response;

public class NoUserWithUUIDException extends AppException {

    public NoUserWithUUIDException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "There is no user with this id", "Please add another id", "");
    }
}
