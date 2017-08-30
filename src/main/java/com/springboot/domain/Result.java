package com.springboot.domain;

import lombok.Data;

/**
 * Http请求返回的最外层对象
 */
@Data
public class Result<T> {

    private Integer code;

    private T msg;

    private Boolean aBoolean;

    private T data;
}
