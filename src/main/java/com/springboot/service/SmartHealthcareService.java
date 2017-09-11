package com.springboot.service;

import com.springboot.domain.PatientHistory;
import com.springboot.domain.Result;
import com.springboot.domain.User;
import com.springboot.domain.XRayTask;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SmartHealthcareService {

    Result login(User user, HttpServletRequest request);
    Result<List<PatientHistory>>  selectPatientHistories(HttpServletRequest request);
    Result<PatientHistory>  selectOnePatientHistoryById(Integer id,HttpServletRequest request);
    Result insertPatientHistory(PatientHistory patientHistory,HttpServletRequest request);
    Result updatePatientHistoryById(PatientHistory patientHistory,HttpServletRequest request);
    Result insertXrayTask(XRayTask xRayTask,HttpServletRequest request);
}