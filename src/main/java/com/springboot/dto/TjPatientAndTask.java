package com.springboot.dto;

import com.springboot.domain.MedicalHistory;
import com.springboot.domain.PatientHistory;
import lombok.Data;

import java.util.List;

@Data
public class TjPatientAndTask {

    private List<MedicalHistory> medicalHistories;
    private PatientHistory patientHistory;
    private List<XRayTaskDTO> XRayTaskDTOList;

}
