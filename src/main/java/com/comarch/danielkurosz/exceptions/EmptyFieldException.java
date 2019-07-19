package com.comarch.danielkurosz.exceptions;

import javax.ws.rs.core.Response;

public class EmptyFieldException extends AppException {

    public EmptyFieldException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(),"Empty field", "Every field must contains data", null);
    }
}
