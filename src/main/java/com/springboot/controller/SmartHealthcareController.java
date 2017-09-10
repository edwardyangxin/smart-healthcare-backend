package com.springboot.controller;

import com.springboot.domain.PatientHistory;
import com.springboot.domain.Result;
import com.springboot.domain.User;
import com.springboot.domain.XRayTask;
import com.springboot.service.SmartHealthcareService;
import com.springboot.tools.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

@RequestMapping(value = "/smart")
@RestController
public class SmartHealthcareController {

    private SmartHealthcareService smartHealthcareService;

    @Autowired
    public SmartHealthcareController(SmartHealthcareService smartHealthcareService) {
        this.smartHealthcareService = smartHealthcareService;
    }

    //登录（医生和专家一个接口）
    @ResponseBody
    @PostMapping(value = "/login")
    public Result login(@Valid @RequestBody User user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return smartHealthcareService.login(user, request);
    }

    //查询一个医生已经建立的的所有病历表
    @ResponseBody
    @GetMapping(value = "/selectAllPatients")
    public Result<List<PatientHistory>> findPatientHistories(HttpServletRequest request) {
        return smartHealthcareService.selectPatientHistories(request);
    }

    //查询一个病历表的详细信息(根据id)
    @GetMapping(value = "/selectOnePatient/{id}")
    public Result findOnePatientHistory(@PathVariable Integer id, HttpServletRequest request) {
        return smartHealthcareService.selectOnePatientHistoryById(id, request);
    }

    //新建病历
    @ResponseBody
    @PostMapping(value = "/newPatient")
    public Result newPatientHistory(@Valid @RequestBody PatientHistory patientHistory, BindingResult bindingResult,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return smartHealthcareService.insertPatientHistory(patientHistory,request);
    }

    //修改病历表(根据id)
    @ResponseBody
    @PostMapping(value = "/updatePatient")
    public Result updatePatientHistory(@Valid @RequestBody PatientHistory patientHistory, BindingResult bindingResult,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return smartHealthcareService.updatePatientHistoryById(patientHistory,request);
    }

    //增加任务表
    @ResponseBody
    @PostMapping(value = "/newXTask")
    public Result newXRayTask(@Valid @RequestBody XRayTask xRayTask, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return smartHealthcareService.insertXrayTask(xRayTask,request);
    }

}
