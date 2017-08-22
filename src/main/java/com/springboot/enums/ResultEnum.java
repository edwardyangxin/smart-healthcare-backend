package com.springboot.enums;

import lombok.Data;

public enum ResultEnum {
    UNKONW_ERROR(-1,"未知错误"),
    SUCCESS(200,"success"),
    NOT_LOGIN(100,"未登录")
    ;
    private Integer code;

    private String msg;

    ResultEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
