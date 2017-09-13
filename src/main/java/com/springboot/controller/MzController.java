package com.springboot.controller;

import com.springboot.domain.PatientHistory;
import com.springboot.dto.Result;
import com.springboot.domain.User;
import com.springboot.domain.XRayTask;
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
        return mzService.login(user, request);
    }

    //查询一个医生已经建立的的所有病历表
    @ResponseBody
    @GetMapping(value = "/selectAllPatients")
    public Result<List<PatientHistory>> findPatientHistories(HttpServletRequest request) {
        return mzService.selectPatientHistories(request);
    }

    //查询一个病历表的详细信息(根据id)
    @GetMapping(value = "/selectOnePatient/{id}")
    public Result findOnePatientHistory(@PathVariable Integer id, HttpServletRequest request) {
        return mzService.selectOnePatientHistoryById(id, request);
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
        return mzService.insertPatientHistory(patientHistory,request);
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
        return mzService.updatePatientHistoryById(patientHistory,request);
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
        return mzService.insertXrayTask(xRayTask,request);
    }

    //修改胸片审查任务表（根据id）
    @ResponseBody
    @PostMapping(value = "/updateXRayTask")
    public Result updateXRayTask(@Valid @RequestBody XRayTask xRayTask, BindingResult bindingResult,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return mzService.updateXRayTaskById(xRayTask,request);
    }

    //查询一个胸片审查任务表的详细信息(根据id)
    @ResponseBody
    @GetMapping(value = "/selectOneXRayTask/{id}")
    public Result findOneXRayTask(@PathVariable Integer id, HttpServletRequest request) {
        return mzService.selectOneXRayTaskById(id, request);
    }

    //查询所有胸片审查任务
    @ResponseBody
    @GetMapping(value = "/selectAllXRayTask")
    public Result<List<XRayTask>> findXRayTasks() {
        return mzService.selectXRayTasks();
    }

}
