package com.springboot.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2017/7/11.
 */

@Entity
@Data
public class TpServiceProvider {
    @Id
    @GeneratedValue
    private Integer id;

    @NotEmpty(message = "供应商名不能为空！")
    @NotNull(message = "供应商名不能为空！")
    private String name;

    private String iconAddress;

    @NotEmpty(message = "密码不能为空！")
    @NotNull(message = "密码不能为空！")
    private String password;

    @NotEmpty(message = "所在城市不能为空！")
    @NotNull(message = "所在城市不能为空！")
    private String city;

    private String businessLicense;

    private String legalRepresentative;

    private String contact;

    private String tel;

    private Integer userId;
    private Integer enterpriseId;


}
