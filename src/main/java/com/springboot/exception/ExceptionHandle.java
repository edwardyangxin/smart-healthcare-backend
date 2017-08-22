package com.springboot.exception;

import com.springboot.domain.Result;
import com.springboot.tools.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof TranslateException){
            TranslateException translateException=(TranslateException) e;
            return ResultUtil.error(translateException.getCode(),translateException.getMessage());
        }else {
            log.error("系统异常{}",e);
            return ResultUtil.error(-100,"未知错误");
        }
    }
}
