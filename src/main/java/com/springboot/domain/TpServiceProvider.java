package com.springboot.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Administrator on 2017/7/11.
 */

@Entity
@Data
public class TpServiceProvider {
    @Id
    @GeneratedValue
    private Integer id;
    private String city;
    private String name;
    private String password;
    private Integer userId;
    private Integer enterpriseId;

}
