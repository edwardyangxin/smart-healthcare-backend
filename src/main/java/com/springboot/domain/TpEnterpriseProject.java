package com.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class TpEnterpriseProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String language;
    private String contact;
    private String tel;
    private String email;
    private String city;
    private String address;
    private String introduce;
    private String cooperationType;
    private String sex;
    private String workTime;
    private String industry;
    private String requirement;
    private String treatment;
    private String qq;
    private Date releaseTime;


}
