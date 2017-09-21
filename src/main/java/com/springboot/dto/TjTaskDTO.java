package com.springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TjTaskDTO {

    private String patientName;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    private String sex;
    private Integer age;
    private Integer dustAge;
    private Integer analysisResult;
    private Integer xRayId;
    private Integer reviewResult;
    private String reviewComment;
    private String filename;

}
