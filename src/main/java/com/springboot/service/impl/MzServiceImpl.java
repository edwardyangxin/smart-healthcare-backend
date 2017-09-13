package com.springboot.service.impl;

import com.springboot.domain.PatientHistory;
import com.springboot.dto.Result;
import com.springboot.domain.User;
import com.springboot.domain.XRayTask;
import com.springboot.enums.ResultEnum;
import com.springboot.mapper.MzMapper;
import com.springboot.service.MzService;
import com.springboot.tools.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class MzServiceImpl implements MzService {

    private MzMapper mzMapper;

    @Autowired
    public MzServiceImpl(MzMapper mzMapper) {
        this.mzMapper = mzMapper;
    }

    @Override
    public Result login(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            if (session.getAttribute("user").toString().equals(user.getName())) {
                return ResultUtil.error(ResultEnum.Repeat_login_Error);
            }
        } catch (NullPointerException e) {
        }
        User userReturn = mzMapper.selectUserByName(user.getName());
        Result userResult = validateUser(user, userReturn);
        if (userResult.getABoolean()) {
            session.setAttribute("user", userReturn.getName());
            session.setAttribute("id", userReturn.getId());
            log.info("用户" + userReturn.getName() + "登陆成功！");
            return ResultUtil.success(userReturn.getUserType());
        }
        return userResult;
    }

    public Result validateUser(User user, User userReturn) {
        try {
            if (userReturn.getId() == null) {
                log.info("用户" + user.getName() + "不存在！");
                return ResultUtil.error(ResultEnum.NOT_EXIST_ERROR);
            }
        } catch (NullPointerException e) {
            log.info("用户" + user.getName() + "不存在！");
            return ResultUtil.error(ResultEnum.NOT_EXIST_ERROR);
        }
        if (!userReturn.getPassword().equals(user.getPassword())) {
            log.info("用户" + user.getName() + "密码输入错误！");
            return ResultUtil.error(ResultEnum.PASSWORD_ERROR);
        }
        return ResultUtil.success();
    }

    @Override
    public Result<List<PatientHistory>> selectPatientHistories(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer id=(Integer) session.getAttribute("id");
        List<PatientHistory> patientHistories = mzMapper.selectPatientHistories(id);
        log.info(name+":查询了所有已建立的病历表");
        return ResultUtil.success(patientHistories);
    }

    @Override
    public Result<PatientHistory> selectOnePatientHistoryById(Integer id,HttpServletRequest request){
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer createdBy = (Integer)session.getAttribute("id");
        PatientHistory patientHistory = mzMapper.selectPatientHistoryById(id,createdBy);
        log.info(name+":查询了一条id为"+id+"病历表");
        return ResultUtil.success(patientHistory);
    }

    @Override
    public Result insertPatientHistory(PatientHistory patientHistory,HttpServletRequest request){
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer id=(Integer) session.getAttribute("id");
        patientHistory.setCreatedOn(new Date());
        patientHistory.setCreatedBy(id);
        mzMapper.insertPatientHistory(patientHistory);
        log.info(name+":新建了一个病历表");
        return ResultUtil.success(ResultEnum.SAVE_SUCCESS);
    }

    @Override
    public Result updatePatientHistoryById(PatientHistory patientHistory,HttpServletRequest request){
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer createdBy = (Integer)session.getAttribute("id");
        patientHistory.setCreatedBy(createdBy);
        mzMapper.updatePatientHistoryById(patientHistory);
        log.info(name+":修改了id为"+patientHistory.getId()+"的病历表");
        return ResultUtil.success(ResultEnum.SAVE_SUCCESS);
    }

    @Override
    public Result insertXrayTask(XRayTask xRayTask,HttpServletRequest request){
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer id=(Integer) session.getAttribute("id");
        xRayTask.setCreatedOn(new Date());
        xRayTask.setCreatedBy(id);
        mzMapper.insertXrayTask(xRayTask);
        log.info(name+":为id="+xRayTask.getPatientHistoryId()+"的病历表，添加了一个胸片审查任务");
        return ResultUtil.success(ResultEnum.SAVE_SUCCESS);
    }

    @Override
    public Result updateXRayTaskById(XRayTask xRayTask,HttpServletRequest request){
        Integer id = xRayTask.getId();
        xRayTask.setId(id);
        mzMapper.updateXRayTaskById(xRayTask);
        log.info("修改了id为"+xRayTask.getId()+"的胸片审查任务表");
        return ResultUtil.success(ResultEnum.SAVE_SUCCESS);
    }

    @Override
    public Result<XRayTask> selectOneXRayTaskById(Integer id,HttpServletRequest request){
        HttpSession session = request.getSession();
        XRayTask xRayTask = mzMapper.selectXRayTaskById(id);
        log.info("查询了一条id为"+id+"的胸片审查任务表");
        return ResultUtil.success(xRayTask);
    }

    @Override
    public Result<List<XRayTask>> selectXRayTasks() {
        List<XRayTask> xRayTasks= mzMapper.selectXRayTasks();
        log.info("查询了所有已建立的胸片审查任务表");
        return ResultUtil.success(xRayTasks);
    }

}