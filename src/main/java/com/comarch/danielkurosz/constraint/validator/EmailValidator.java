package com.comarch.danielkurosz.constraint.validator;

import com.comarch.danielkurosz.constraint.Email;
import org.hibernate.validator.internal.util.logging.Log;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {

    Pattern invalidCharacter = null;

    @Override
    public void initialize(Email constraintAnnotation) {
        invalidCharacter = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
                "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.)" +
                "{3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]" +
                ":(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\" +
                "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))");
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext context) {
        if( object == null || (!invalidCharacter.matcher(object).find())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Wrong email").addConstraintViolation();
            return false;
        }
        Logger.getAnonymousLogger().info("Email Validator : Good email");
        return true;
    }
}
