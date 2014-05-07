package com.agoda.image.calculator.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/2/14
 * Time: 7:13 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ErrorCode {
    UNABLE_TO_DOWNLOAD(10, "Unable to check/download"),
    IO_PROBLEM(20, "Unable to read/write file from/to the file system")
    ;

    private int errorCode;
    private String message;

    private ErrorCode(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
