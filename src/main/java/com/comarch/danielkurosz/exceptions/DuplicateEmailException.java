package com.comarch.danielkurosz.exceptions;

import javax.ws.rs.core.Response;

public class DuplicateEmailException extends AppException {

    public DuplicateEmailException() {
        super(404, Response.Status.BAD_REQUEST.getStatusCode(), "This email already exists","Please add another email or contact with admin","");
    }
}
