package com.springboot.dto;

import com.springboot.domain.MzMedicalHistory;
import com.springboot.domain.MzPatientHistory;
import lombok.Data;

import java.util.List;

@Data
public class MzPatientAndTask {

    private List<MzMedicalHistory> mzMedicalHistories;
    private MzPatientHistory mzPatientHistory;
    private List<MzXRayTaskDTO> mzXRayTaskDTOList;

}
