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

    private String realName;

    private String name;

    private String password;

    private String tel;

    private String email;

    private String gender ;

    private String idCard;

    private String location;

    private String iconAddress;

    private Boolean status;

    private String activeCode;


}
