package com.springboot.dto;

import lombok.Data;

@Data
public class TjTaskDTO {

    private String patientName;
    private String sex;
    private Integer age;
    private Integer dustAge;
    private Integer analysisResult;
    private Integer xRayId;
    private Integer reviewResult;
    private String reviewComment;
    private String filename;

}
