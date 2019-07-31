package com.comarch.danielkurosz.constraint;


import com.comarch.danielkurosz.constraint.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface Email {
    String message() default "Email format incorrect";

// an attribute groups that allows the specification of validation groups,
// to which this constraint belongs(default to an empty array of type Class<?>.)
    Class<?>[] groups() default { };

//an attribute payload that can be used by clients of the Bean Validation API to assign custom payload objects to a constraint.
// This attribute is not used by the API itself
    Class<? extends Payload>[] payload() default { };

}
