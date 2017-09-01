package com.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class TpPersonInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String address;

    private String language;

    private Integer age;

    private String city;

    private String education;

    private String tel;

    private String salaryRange;

    private String workingYears;

    private String email;

    private String introduce;

    private String projectExperience;

    private String translateType;

    private String industry;

    private String cooperationType;

    private String workType;

    private Date registerTime;

    private String iconAddress;

    private String uuid;

    private Integer clickAmount;

    private Integer stars;

    private Boolean serviceProvider;

     private String specialty;

}
