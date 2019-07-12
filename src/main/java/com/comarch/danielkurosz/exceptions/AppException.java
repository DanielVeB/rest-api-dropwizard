package com.comarch.danielkurosz.exceptions;

public class AppException extends Exception {

    private Integer status;

    /** application specific error code */
    private int code;

    /** link documenting the exception */
    private String link;

    /** detailed error description for developers*/
    private String developerMessage;

    /**
     *
     * @param status contains the same HTTP Status code returned by the server
     * @param code application specific error code
     * @param message message describing the error
     * @param developerMessage extra information that might useful for developers
     * @param link link point to page where the error message is documented
     */
    public AppException(int status, int code, String message,
                        String developerMessage, String link) {
        super(message);
        this.status = status;
        this.code = code;
        this.developerMessage = developerMessage;
        this.link = link;
    }

    public AppException() { }

     int getStatus() {
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

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
