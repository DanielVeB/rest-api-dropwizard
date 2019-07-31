package com.comarch.danielkurosz.exceptions.mapper;

import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.EmptyFieldException;
import com.comarch.danielkurosz.exceptions.InvalidEmailException;

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

        List<String> messages = new LinkedList<>();
        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            messages.add(constraintViolation.getMessage());
            //because EmptyFieldException has main priority
            if (constraintViolation.getMessage().equals("may not be null")) {
                return appExceptionMapper.toResponse(new EmptyFieldException());
            }
        }
        // if EmptyFieldException wasn't found, throw other exception
        if (messages.get(0).equals("Wrong email")) {
            return appExceptionMapper.toResponse(new InvalidEmailException());
        }
        else{
            return appExceptionMapper.toResponse(new AppException(400,"","",""));
        }
    }
}
