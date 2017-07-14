package com.springboot.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

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

    @NotEmpty(message = "密码不能为空！")
    @NotNull(message = "密码不能为空！")
    private String password;

    @NotEmpty(message = "所在城市不能为空！")
    @NotNull(message = "所在城市不能为空！")
    private String city;

    private Integer userId;
    private Integer enterpriseId;




}
