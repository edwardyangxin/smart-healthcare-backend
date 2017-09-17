package com.springboot.dto;

import lombok.Data;

@Data
public class MzTaskDTO {

    private String patientName;
    private String sex;
    private Integer age;
    private Integer dustAge;
    private Integer analysisResult;
    private Integer xRayId;
    private Integer reviewResult;
    private Integer reviewComment;
    private String filename;

}
