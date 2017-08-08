package com.springboot.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2017/7/11.
 */
@Entity
@Data
public class TpPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "真实姓名不能为空！")
    @NotNull(message = "真实姓名不能为空！")
    private String realName;

    @NotEmpty(message = "用户姓名不能为空！")
    @NotNull(message = "用户姓名不能为空！")
    private String name;

    @NotEmpty(message = "密码不能为空！")
    @NotNull(message = "密码不能为空！")
    @Size(min = 6, max = 16, message = "密码长度必须在6到16之间！")
    private String password;

    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号格式不正确！")
    @NotEmpty(message = "手机号码不能为空！")
    @NotNull(message = "手机号码不能为空！")
    private String tel;

    @NotEmpty(message = "邮箱地址不能为空！")
    @NotNull(message = "邮箱地址不能为空！")
    @Email(message = "邮箱地址不符合规范！")
    private String email;

    private String idCard;

    private String location;

    private String iconAddress;


}
