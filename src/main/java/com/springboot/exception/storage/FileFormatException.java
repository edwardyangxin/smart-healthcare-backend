package com.springboot.exception.storage;

/**
 * Created by liuyongg on 27/7/2017.
 */

public class FileFormatException extends RuntimeException {

    public FileFormatException(String message) {
        super(message);
    }

    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
