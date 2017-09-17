package com.springboot.service.impl;

import com.springboot.domain.*;
import com.springboot.dto.MzPatientXRayTask;
import com.springboot.dto.MzTaskDTO;
import com.springboot.dto.MzTasksDTO;
import com.springboot.dto.Result;
import com.springboot.enums.ResultEnum;
import com.springboot.mapper.FileUploadMapper;
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
    private FileUploadMapper fileUploadMapper;

    @Autowired
    public MzServiceImpl(MzMapper mzMapper,FileUploadMapper fileUploadMapper) {
        this.mzMapper = mzMapper;
        this.fileUploadMapper = fileUploadMapper;
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
    public Result<List<MzPatientHistory>> selectMzPatientHistories(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer id = (Integer) session.getAttribute("id");
        List<MzPatientHistory> mzPatientHistories = mzMapper.selectMzPatientHistories(id);
        log.info(name + ":查询了所有已建立的病历表");
        return ResultUtil.success(mzPatientHistories);
    }

    @Override
    public Result<MzPatientHistory> selectOneMzPatientHistoryById(Integer id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer createdBy = (Integer) session.getAttribute("id");
        MzPatientHistory mzPatientHistory = mzMapper.selectMzPatientHistoryById(id, createdBy);
        log.info(name + ":查询了一条id为" + id + "病历表");
        return ResultUtil.success(mzPatientHistory);
    }

    @Override
    public Result insertMzPatientHistory(MzPatientHistory mzPatientHistory, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer id = (Integer) session.getAttribute("id");
        mzPatientHistory.setCreatedOn(new Date());
        mzPatientHistory.setCreatedBy(id);
        mzMapper.insertMzPatientHistory(mzPatientHistory);
        log.info(name + ":新建了一个病历表");
        return ResultUtil.success(ResultEnum.SAVE_SUCCESS);
    }

    @Override
    public Result insertMzPatientHistoryAndXTask(MzPatientXRayTask mzPatientXRayTask, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer id = (Integer) session.getAttribute("id");

        MzPatientHistory mzPatientHistory = mzPatientXRayTask.getMzPatientHistory();
        Date data = new Date();
        mzPatientHistory.setCreatedOn(data);
        mzPatientHistory.setCreatedBy(id);
        mzMapper.insertMzPatientHistory(mzPatientHistory);

        MzXrayTask mzXrayTask = new MzXrayTask();
        mzXrayTask.setCreatedOn(data);
        mzXrayTask.setCreatedBy(mzPatientHistory.getId());
        mzXrayTask.setXRayId(mzPatientXRayTask.getFile());
        mzMapper.insertMzXrayTask(mzXrayTask);
        log.info(name + ":新建了一个病历表,并为id=" + mzPatientHistory.getId() + "的病历表，添加了一个胸片审查任务");

        return ResultUtil.success(ResultEnum.SAVE_SUCCESS);

    }


    @Override
    public Result updateMzPatientHistoryById(MzPatientHistory mzPatientHistory, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer createdBy = (Integer) session.getAttribute("id");
        mzPatientHistory.setCreatedBy(createdBy);
        mzMapper.updateMzPatientHistoryById(mzPatientHistory);
        log.info(name + ":修改了id为" + mzPatientHistory.getId() + "的病历表");
        return ResultUtil.success(ResultEnum.SAVE_SUCCESS);
    }


    @Override
    public Result insertMzXrayTask(MzXrayTask mzXrayTask, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer id = (Integer) session.getAttribute("id");
        mzXrayTask.setCreatedOn(new Date());
        mzXrayTask.setCreatedBy(id);
        mzMapper.insertMzXrayTask(mzXrayTask);
        log.info(name + ":为id=" + mzXrayTask.getPatientHistoryId() + "的病历表，添加了一个胸片审查任务");
        return ResultUtil.success(ResultEnum.SAVE_SUCCESS);
    }

    @Override
    public Result updateMzXrayTaskById(MzXrayTask mzXrayTask, HttpServletRequest request) {
        Integer id = mzXrayTask.getId();
        mzXrayTask.setId(id);
        mzMapper.updateMzXrayTaskById(mzXrayTask);
        log.info("修改了id为" + mzXrayTask.getId() + "的胸片审查任务表");
        return ResultUtil.success(ResultEnum.SAVE_SUCCESS);
    }

    @Override
    public Result selectOneMzXrayTaskById(Integer id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MzTaskDTO mzTaskDTO = mzMapper.selectMzXrayTaskByIds(id);
        Integer xRayId = mzTaskDTO.getXRayId();
        String name = fileUploadMapper.selectUploadFileById(xRayId).getFileName();
        mzTaskDTO.setFilename(name);
        log.info("查询了一条id为" + id + "的胸片审查任务表");
        return ResultUtil.success(mzTaskDTO);
    }

    @Override
    public Result<List<MzTasksDTO>> selectMzXrayTasks() {
        List<MzTasksDTO> mzTasksDTOS = mzMapper.selectMzXrayTasks();
        log.info("查询了所有已建立的胸片审查任务表");
        return ResultUtil.success(mzTasksDTOS);
    }

    @Override
    public Result isNeedExpert(Integer id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Boolean need = mzMapper.selectMzXrayTaskById(id).getNeed();
        if (need) {
            Boolean aBoolean = false;
            mzMapper.updateMzXrayTaskNeedById(id, aBoolean);
            log.info(name + ":取消选择院外专家");
            return ResultUtil.success("取消选择院外专家", aBoolean);
        } else {
            Boolean aBoolean = true;
            mzMapper.updateMzXrayTaskNeedById(id, aBoolean);
            log.info(name + ":选择了院外专家");
            return ResultUtil.success("选择了院外专家", aBoolean);
        }
    }

    @Override
    public Result<List<MzXrayTask>> selectAllMzOutExpertTasks(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer id = (Integer) session.getAttribute("id");

        List<MzXrayTask> mzXrayTasks = mzMapper.selectAllMzOutExpertTasks();
        log.info(name + ":查询了所有需要院外专家处理的任务表");
        return ResultUtil.success(mzXrayTasks);
    }

    @Override
    public Result updateOneMzOutExpertTask(MzXrayTask mzXrayTask,HttpServletRequest request){
        HttpSession session = request.getSession();
        String name = session.getAttribute("user").toString();
        Integer id = (Integer) session.getAttribute("id");

        mzMapper.updateOneMzOutExpertTask(mzXrayTask);
        log.info("院外医生：" + name + ":处理了id为"+mzXrayTask.getId()+"的任务表");
        return ResultUtil.success(ResultEnum.SAVE_SUCCESS);


    }
}