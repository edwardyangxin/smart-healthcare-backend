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
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer patientHistoryId;
    private String description;

    // @DateTimeFormat(pattern="yyyy-MM-dd")

    // @Temporal(TemporalType.DATE)
    //@DateTimeFormat(pattern="yyyy-MM-dd")
    // @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    // @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /* public String serStartTime() {
        return "2018-10";
    }*/

}
