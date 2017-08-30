package com.springboot.tools;

import com.springboot.domain.Result;
import com.springboot.enums.ResultEnum;

public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("success");
        result.setABoolean(true);
        result.setData(object);
        return result;
    }

    public static Result success(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        result.setABoolean(true);
        return result;
    }

 /*   public static Result success() {
        return success(null);
    }*/

    public static Result success() {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("success");
        result.setABoolean(true);
        return result;
    }

    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        result.setABoolean(false);
        return result;
    }

    public static Result error(Object object) {
        Result result = new Result();
        //result.setCode(400);
        result.setABoolean(false);
        result.setMsg(object);
        return result;
    }


}
