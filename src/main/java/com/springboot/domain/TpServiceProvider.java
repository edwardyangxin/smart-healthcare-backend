package com.springboot.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Administrator on 2017/7/11.
 */

@Entity
@Data
public class TpServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

   /* @NotEmpty(message = "供应商名不能为空！")
    @NotNull(message = "供应商名不能为空！")*/
    private String name;

    private String companyName;

    private String iconAddress;

   /* @NotEmpty(message = "密码不能为空！")
    @NotNull(message = "密码不能为空！")*/
    private String password;

  /*  @NotEmpty(message = "所在城市不能为空！")
    @NotNull(message = "所在城市不能为空！")*/
    private String city;
    private String businessLicense;

    private String legalRepresentative;

    private String contact;

  /*  @NotEmpty(message = "联系人电话不能为空！")
    @NotNull(message = "联系人电话不能为空！")*/
    private String tel;

    private Integer userId;
    private Integer enterpriseId;

    private Boolean status;
    private String activeCode;
    private String uuid;
    private String industy;

   /* @NotEmpty(message = "邮箱不能为空！")
    @NotNull(message = "邮箱不能为空！")*/
    private String email;

}
