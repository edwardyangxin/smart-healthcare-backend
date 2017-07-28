package com.springboot.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TpEnterpriseProjects {
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

}
