package com.springboot.dto;

import lombok.Data;

/**
 * Created by liuyongg on 29/8/2017.
 */

@Data
public class LoginReturn {

    private String password;

    //是否激活的状态
    private Boolean status;

    private String uuid;
}
