package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.InvalidEmailException;

import javax.ws.rs.core.Response;

public class ClientValidator {

    public static void validate(ClientDTO clientDTO) throws AppException {

        // check correctness of email
        //regex from https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
                "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.)" +
                "{3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]" +
                ":(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\" +
                "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))";

        if (clientDTO.getEmail() == null || clientDTO.getFirstName() == null || clientDTO.getLastName() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 404, "Empty fields", "", "link");
        }
        if (!clientDTO.getEmail().matches(regex)) {
            throw new InvalidEmailException();
        }
    }
}
