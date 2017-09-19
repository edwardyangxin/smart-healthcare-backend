package com.springboot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Data
@Entity
public class PatientHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String patientName;

    private String sex;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    private String pid;
    private String tel;
    private String job;
    private String jobHistory;

    private Integer dustAge;
    private String dustProperty;
    private Integer createdBy;
    private Date createdOn;
}
