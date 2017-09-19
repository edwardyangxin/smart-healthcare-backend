package com.springboot.dto;

import com.springboot.domain.MedicalHistory;
import com.springboot.domain.MzPatientHistory;
import lombok.Data;

import java.util.List;

@Data
public class MzPatientXRayTask {
    private MzPatientHistory mzPatientHistory;
    private List<MedicalHistory> medicalHistories;
    private Integer file;
}
