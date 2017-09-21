package com.springboot.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class MzXRayTaskDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String createdBy;
    private Integer patientHistoryId;
    private String expertId;
    private Integer reviewResult;
    private String reviewComment;
    private Integer analysisResult;
    private Integer status;
    private Integer xRayId;

    private Date createdOn;
    private String fileName;

    private String outexpertId;
    private String outreviewResult;
    private String outreviewComment;
    private Boolean need;



}
