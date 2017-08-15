package com.springboot.dto.updto;


/**
 * Created by liuyongg on 7/8/2017.
 */

public class ControllerResponse {

    private String message;
    private boolean aBoolean;


    public ControllerResponse(String message, boolean aBoolean) {
        this.message = message;
        this.aBoolean = aBoolean;
    }

    public static ControllerResponse create(String message, boolean aBoolean) {
        return new ControllerResponse(message, aBoolean);
    }

    public String getMessage() {
        return message;
    }

    public boolean getABoolean() {
        return aBoolean;
    }

}