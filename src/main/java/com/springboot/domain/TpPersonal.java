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
    private String userName;
    private String password;
    private String tel;
    private String email;
}
