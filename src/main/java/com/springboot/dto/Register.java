package com.springboot.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Register {

    @Id
    @GeneratedValue
    private Integer id;

    @NotEmpty(message = "用户姓名不能为空！")
    @NotNull(message = "用户姓名不能为空！")
    private String name;

    @NotEmpty(message = "密码不能为空！")
    @NotNull(message = "密码不能为空！")
    @Size(min = 6, max = 16, message = "密码长度必须在6到16之间！")
    private String password;

    @NotEmpty(message = "邮箱地址不能为空！")
    @NotNull(message = "邮箱地址不能为空！")
    @Email(message = "邮箱地址不符合规范！")
    private String email;

    private Boolean status;

    private String activeCode;

    private String category;

    private String clientCode;
}
