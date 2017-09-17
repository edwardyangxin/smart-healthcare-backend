package com.springboot.dto;

import com.springboot.domain.MzPatientHistory;
import lombok.Data;

@Data
public class MzPatientXRayTask {
    private MzPatientHistory mzPatientHistory;
    private Integer file;
}
