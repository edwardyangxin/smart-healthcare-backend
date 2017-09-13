package com.springboot.service;

import com.springboot.domain.PatientHistory;
import com.springboot.domain.User;
import com.springboot.domain.XRayTask;
import com.springboot.dto.PatientXRayTask;
import com.springboot.dto.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TjService {

    Result login(User user, HttpServletRequest request);
    Result<List<PatientHistory>>  selectPatientHistories(HttpServletRequest request);
    Result<PatientHistory>  selectOnePatientHistoryById(Integer id, HttpServletRequest request);
    Result insertPatientHistory(PatientHistory patientHistory, HttpServletRequest request);
    Result insertPatientHistoryAndXTask(PatientXRayTask patientXRayTask,HttpServletRequest request);
    Result updatePatientHistoryById(PatientHistory patientHistory, HttpServletRequest request);
    Result insertXrayTask(XRayTask xRayTask, HttpServletRequest request);
    Result updateXRayTaskById(XRayTask xRayTask, HttpServletRequest request);
    Result<XRayTask>  selectOneXRayTaskById(Integer id, HttpServletRequest request);
    Result<List<XRayTask>>  selectXRayTasks();
}
