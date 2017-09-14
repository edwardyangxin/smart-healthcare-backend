package com.springboot.controller;

import com.springboot.domain.MzPatientHistory;
import com.springboot.domain.MzXrayTask;
import com.springboot.domain.User;
import com.springboot.dto.Result;
import com.springboot.service.MzService;
import com.springboot.tools.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RequestMapping(value = "/TM/MZ")
@RestController
public class MzController {

    private MzService mzService;

    @Autowired
    public MzController(MzService mzService) {
        this.mzService = mzService;
    }

    //登录（医生、院内专家，院外专家一个接口）
    @ResponseBody
    @PostMapping(value = "/login")
    public Result login(@Valid @RequestBody User user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return mzService.login(user, request);
    }

    //查询一个医生已经建立的的所有病历表
    @ResponseBody
    @GetMapping(value = "/selectAllMzPatients")
    public Result<List<MzPatientHistory>> findMzPatientHistories(HttpServletRequest request) {
        return mzService.selectMzPatientHistories(request);
    }

    //查询一个病历表的详细信息(根据id)
    @GetMapping(value = "/selectOneMzPatient/{id}")
    public Result findOneMzPatientHistory(@PathVariable Integer id, HttpServletRequest request) {
        return mzService.selectOneMzPatientHistoryById(id, request);
    }

    //新建病历
    @ResponseBody
    @PostMapping(value = "/newMzPatient")
    public Result newMzPatientHistory(@Valid @RequestBody MzPatientHistory mzPatientHistory, BindingResult bindingResult,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return mzService.insertMzPatientHistory(mzPatientHistory,request);
    }

    //修改病历表(根据id)
    @ResponseBody
    @PostMapping(value = "/updateMzPatient")
    public Result updateMzPatientHistory(@Valid @RequestBody MzPatientHistory mzPatientHistory, BindingResult bindingResult,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return mzService.updateMzPatientHistoryById(mzPatientHistory,request);
    }

    //增加任务表
    @ResponseBody
    @PostMapping(value = "/newMzXTask")
    public Result newMzXrayTask(@Valid @RequestBody MzXrayTask mzXrayTask, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return mzService.insertMzXrayTask(mzXrayTask,request);
    }

    //修改胸片审查任务表（根据id）
    @ResponseBody
    @PostMapping(value = "/updateMzXrayTask")
    public Result updateMzXrayTask(@Valid @RequestBody MzXrayTask mzXrayTask, BindingResult bindingResult,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return mzService.updateMzXrayTaskById(mzXrayTask,request);
    }

    //查询一个胸片审查任务表的详细信息(根据id)
    @ResponseBody
    @GetMapping(value = "/selectOneMzXrayTask/{id}")
    public Result findOneMzXrayTask(@PathVariable Integer id, HttpServletRequest request) {
        return mzService.selectOneMzXrayTaskById(id, request);
    }

    //查询所有胸片审查任务
    @ResponseBody
    @GetMapping(value = "/selectAllMzXrayTask")
    public Result<List<MzXrayTask>> findXRayTasks() {
        return mzService.selectMzXrayTasks();
    }
}
