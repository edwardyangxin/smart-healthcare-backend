package com.springboot.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by liuyongg on 17/7/2017.
 */
@Data
public class Login {

    @NotEmpty(message = "用户名不能为空！")
    @NotNull(message = "用户名不能为空！")
    private String name;

    @NotEmpty(message = "密码不能为空！")
    @NotNull(message = "密码不能为空！")
    @Size(min = 6, max = 16, message = "密码长度必须在6到16之间！")
    private String password;

}
