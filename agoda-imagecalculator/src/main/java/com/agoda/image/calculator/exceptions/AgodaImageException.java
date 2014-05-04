package com.agoda.image.calculator.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/2/14
 * Time: 7:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgodaImageException extends Exception {
    private ErrorCode errorCode;

    public AgodaImageException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public AgodaImageException(ErrorCode errorCode, String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
        this.errorCode = errorCode;
    }

    public AgodaImageException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
