package com.springboot.dto.updto;

/**
 * Created by liuyongg on 27/7/2017.
 */
public class ExceptionResponse {

    private String message;
    private Integer code;


    public ExceptionResponse(Integer code, String message){
        this.message = message;
        this.code = code;
    }

    public static ExceptionResponse create(Integer code, String message){
        return new ExceptionResponse(code, message);
    }

    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

}