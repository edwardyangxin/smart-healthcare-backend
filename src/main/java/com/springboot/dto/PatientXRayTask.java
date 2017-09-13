package com.springboot.dto;

import com.springboot.domain.PatientHistory;
import com.springboot.domain.XRayTask;
import lombok.Data;

@Data
public class PatientXRayTask {
    private PatientHistory patientHistory;
    private XRayTask xRayTask;
}
