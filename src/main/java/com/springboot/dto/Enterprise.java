package com.springboot.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by liuyongg on 18/7/2017.
 */
@Data
public class Enterprise {

    private String name;

    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号码格式不正确！")
    private String tel;
    @NotEmpty(message = "邮箱地址不能为空！")
    @NotNull(message = "邮箱地址不能为空！")
    @Email(message = "邮箱地址不符合规范！")
    private String email;
}
