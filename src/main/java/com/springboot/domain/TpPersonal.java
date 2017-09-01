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
public class TpPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String realName;

    private String name;

    private String password;

    private String tel;

    private String email;

    private String gender;

    private String idCard;

    private String location;

    private String iconAddress;

    private Boolean status;

    private String activeCode;

    private String uuid;

    private  String city;

    private String language;



}
