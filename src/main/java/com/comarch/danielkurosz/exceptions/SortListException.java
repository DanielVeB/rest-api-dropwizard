package com.comarch.danielkurosz.exceptions;

import javax.ws.rs.core.Response;

public class SortListException extends AppException {

    public SortListException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "Wrong sorting list", "List should matches regex : (((firstName)|(lastName)|(email)):((asc)|(desc)),)*", null);
    }
}
