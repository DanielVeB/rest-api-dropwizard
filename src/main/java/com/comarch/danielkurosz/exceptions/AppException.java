package com.comarch.danielkurosz.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends Exception {

    private Integer status;

    /**
     * application specific error code
     */
    private int code;

    /**
     * link documenting the exception
     */
    private String link;

    /**
     * extra information how can it be repaired
     */
    private String advice;

    /**
     * @param code    application specific error code
     * @param message message describing the error
     * @param advice  extra information how can it be repaired
     * @param link    link point to page where the error message is documented
     */
    public AppException(int code, String message,
                        String advice, String link) {
        super(message);
        this.status = code;
        this.code = code;
        this.advice = advice;
        this.link = link;
    }
}
