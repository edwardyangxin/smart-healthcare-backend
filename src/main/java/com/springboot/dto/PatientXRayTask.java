package com.springboot.dto;

import com.springboot.domain.PatientHistory;
import lombok.Data;

@Data
public class PatientXRayTask  {
    private Integer id;
    private PatientHistory patientHistory;
}
