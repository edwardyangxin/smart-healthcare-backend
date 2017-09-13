package com.springboot.dto;

import com.springboot.domain.OutXRayTask;
import com.springboot.domain.OutpatientHistory;
import lombok.Data;

@Data
public class OutPatientXRayTask {
    private OutpatientHistory outpatientHistory;
    private OutXRayTask outXRayTask;
}
