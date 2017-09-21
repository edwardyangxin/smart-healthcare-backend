package com.springboot.service;

import com.springboot.domain.MzPatientHistory;
import com.springboot.domain.MzXrayTask;
import com.springboot.domain.User;
import com.springboot.dto.MzPatientXRayTask;
import com.springboot.dto.MzTasksDTO;
import com.springboot.dto.Pid;
import com.springboot.dto.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MzService {

    Result login(User user, HttpServletRequest request);
    Result<List<MzPatientHistory>>  selectMzPatientHistories(HttpServletRequest request);
    Result<MzPatientXRayTask>  selectOneMzPatientHistoryById(Integer id,HttpServletRequest request);
    Result insertMzPatientHistory(MzPatientHistory MzPatientHistory,HttpServletRequest request);
    Result insertMzPatientHistoryAndXTask(MzPatientXRayTask mzPatientXRayTask, HttpServletRequest request);
    Result updateMzPatientHistoryById(MzPatientXRayTask mzPatientXRayTask,HttpServletRequest request);
    Result insertMzXrayTask(MzXrayTask mzXrayTask, HttpServletRequest request);
    Result updateMzXrayTaskById(MzXrayTask mzXrayTask,HttpServletRequest request);
    Result  selectOneMzXrayTaskById(Integer id,HttpServletRequest request);
    Result<List<MzTasksDTO>>  selectMzXrayTasks();
    Result isNeedExpert(Integer id,HttpServletRequest request);
    Result<List<MzXrayTask>> selectAllMzOutExpertTasks(HttpServletRequest request);
    Result updateOneMzOutExpertTask(MzXrayTask mzXrayTask,HttpServletRequest request);
    Result selectByPid(Pid pid);
    Result selectMzPatientAndXTask(Integer id, HttpServletRequest request);
}
