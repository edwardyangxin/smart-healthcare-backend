package com.springboot.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by liuyongg on 17/7/2017.
 */
@Data
public class Password {

    private String uuid;

    @NotEmpty(message = "旧密码不能为空！")
    @NotNull(message = "旧密码不能为空！")
    @Size(min = 6, max = 16, message = "旧密码长度必须在6到16之间！")
    private String password;

    @NotEmpty(message = "新密码不能为空！")
    @NotNull(message = "新密码不能为空！")
    @Size(min = 6, max = 16, message = "新密码长度必须在6到16之间！")
    private String newPassword;

    @NotEmpty(message = "再次输入的新密码不能为空！")
    @NotNull(message = "再次输入的新密码不能为空！")
    @Size(min = 6, max = 16, message = "再次输入的新密码长度必须在6到16之间！")
    private String retypePassword;
}
