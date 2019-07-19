package com.comarch.danielkurosz.dto;

import com.comarch.danielkurosz.exceptions.AppException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

@Getter
@Setter
public class ExceptionDTO {

    /**
     * application specific error code
     */
    private int code;

    /**
     * message describing the error
     */
    private String message;

    /**
     * link point to page where the error message is documented
     */
    private String link;

    /**
     * extra information how it can be repaired
     */
    private String advice;

    public ExceptionDTO(AppException ex) {
        try {
            BeanUtils.copyProperties(this, ex);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public ExceptionDTO() {
    }
}
