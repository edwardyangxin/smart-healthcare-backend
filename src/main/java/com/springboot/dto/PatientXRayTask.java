package com.springboot.dto;

import com.springboot.domain.MedicalHistory;
import com.springboot.domain.PatientHistory;
import lombok.Data;

import java.util.List;

@Data
public class PatientXRayTask  {
    private Integer file;
    private List<MedicalHistory> medicalHistories;
    private PatientHistory patientHistory;
}
