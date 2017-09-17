package com.springboot.dto;

import lombok.Data;

@Data
public class MzTasksDTO {

    private Integer taskId;

    private String name;

    private String patientName;
    private String pid;

    private Integer status;
    private Integer reviewResult;
    private Integer analysisResult;
}
