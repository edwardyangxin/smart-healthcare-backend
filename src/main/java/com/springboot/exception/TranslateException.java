package com.springboot.exception;

import com.springboot.enums.ResultEnum;
import lombok.Data;

@Data
public class TranslateException extends RuntimeException {
    private Integer code;

    public TranslateException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
