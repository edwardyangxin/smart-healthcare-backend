package com.springboot.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2017/7/11.
 */
@Data
@Entity
public class TpEnterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "企业名不能为空！")
    @NotNull(message = "企业名不能为空！")
    private String name;

    @NotEmpty(message = "密码不能为空！")
    @NotNull(message = "密码不能为空！")
    @Size(min = 6, max = 16, message = "密码长度必须在6到16之间！")
    private String password;

    private String businessLicense;

    private String legalRepresentative;

    private String contact;

    private String tel;

    private String industry;

    private String city;

    private String iconAddress;

    private Boolean status;

    private String activeCode;

    private String uuid;

    @NotEmpty(message = "邮箱地址不能为空！")
    @NotNull(message = "邮箱地址不能为空！")
    @Email(message = "邮箱地址不符合规范！")
    private String email;
}
