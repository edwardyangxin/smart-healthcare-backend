package com.springboot.dto;

import com.springboot.domain.MzMedicalHistory;
import com.springboot.domain.MzPatientHistory;
import lombok.Data;

import java.util.List;

@Data
public class MzPatientXRayTask {

    private MzPatientHistory mzPatientHistory;
    private List<MzMedicalHistory> mzMedicalHistories;
    private Integer file;
}
