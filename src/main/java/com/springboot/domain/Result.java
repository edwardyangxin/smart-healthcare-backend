package com.springboot.domain;

import lombok.Data;

/**
 * Http请求返回的最外层对象
 */
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;
}