package com.comarch.danielkurosz.dto;

import com.comarch.danielkurosz.exceptions.AppException;
import org.apache.commons.beanutils.BeanUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.InvocationTargetException;

@XmlRootElement
public class ExceptionDTO{

    /** contains the same HTTP Status code returned by the server */
    @XmlElement(name = "status")
    private int status;

    /** application specific error code */
    @XmlElement(name = "code")
    private int code;

    /** message describing the error*/
    @XmlElement(name = "message")
    private String message;

    /** link point to page where the error message is documented */
    @XmlElement(name = "link")
    private String link;

    /** extra information how it can be repaired */
    @XmlElement(name = "advice")
    private String advice;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ExceptionDTO(AppException ex){
        try {
            BeanUtils.copyProperties(this, ex);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public ExceptionDTO() {}
}
