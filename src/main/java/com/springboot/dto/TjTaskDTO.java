package com.springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.domain.MedicalHistory;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TjTaskDTO {

    private String patientName;
    private Integer patientHistoryId;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    private String sex;
    private Integer age;
    private Integer dustAge;
    private Integer analysisResult;
    private Integer xRayId;
    private Integer reviewResult;
    private String reviewComment;
    private String dustProperty;
    private String filename;

    private List<MedicalHistory> medicalHistories;

}
