package com.springboot.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class MzXrayTaskback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer createdBy;
    private Integer patientHistoryId;
    private Integer expertId;
    private Integer reviewResult;
    private String reviewComment;
    private Integer analysisResult;
    private Integer status;
    private Integer fileId;
    private Date createdOn;
    private Integer outexpertId;
    private String outreviewResult;
    private String outreviewComment;
    private Boolean need;
}
