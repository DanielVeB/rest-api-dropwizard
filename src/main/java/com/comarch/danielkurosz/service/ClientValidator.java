package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.EmptyFieldException;
import com.comarch.danielkurosz.exceptions.InvalidEmailException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ClientValidator {

    public static void validate(ClientDTO clientDTO)  {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
//        System.out.println(violations);
//        String message = violations.iterator().next().getMessage();
//        System.out.println(message);
//        if (message.equals("may not be null")) {
//            throw new EmptyFieldException();
//        } else if (message.equals("Wrong email")) {
//            throw new InvalidEmailException();
//        }
    }
}
