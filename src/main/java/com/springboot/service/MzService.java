package com.springboot.service;

import com.springboot.domain.MzPatientHistory;
import com.springboot.domain.MzXrayTask;
import com.springboot.domain.User;
import com.springboot.domain.XRayTask;
import com.springboot.dto.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MzService {

    Result login(User user, HttpServletRequest request);
    Result<List<MzPatientHistory>>  selectMzPatientHistories(HttpServletRequest request);
    Result<MzPatientHistory>  selectOneMzPatientHistoryById(Integer id,HttpServletRequest request);
    Result insertMzPatientHistory(MzPatientHistory MzPatientHistory,HttpServletRequest request);
    Result updateMzPatientHistoryById(MzPatientHistory MzPatientHistory,HttpServletRequest request);
    Result insertMzXrayTask(MzXrayTask mzXrayTask, HttpServletRequest request);
    Result updateMzXrayTaskById(MzXrayTask mzXrayTask,HttpServletRequest request);
    Result<MzXrayTask>  selectOneMzXrayTaskById(Integer id,HttpServletRequest request);
    Result<List<MzXrayTask>>  selectMzXrayTasks();
}
