package com.comarch.danielkurosz.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.LinkedList;
import java.util.List;

@Provider
public class ConstraintViolationExceptionMapper implements
        ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {

        AppExceptionMapper appExceptionMapper = new AppExceptionMapper();

        System.out.println(exception.getConstraintViolations());


        List<String> messages = new LinkedList<>();
        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            messages.add(constraintViolation.getMessage());
            if (constraintViolation.getMessage().equals("may not be null")) {
                return appExceptionMapper.toResponse(new EmptyFieldException());
            }
        }
        if (messages.get(0).equals("Wrong email")) {
            return appExceptionMapper.toResponse(new InvalidEmailException());
        }
        return null;
    }
}
