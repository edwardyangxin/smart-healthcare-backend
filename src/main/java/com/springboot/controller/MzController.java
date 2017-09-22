package com.springboot.controller;

import com.springboot.domain.MzPatientHistory;
import com.springboot.domain.MzXrayTask;
import com.springboot.domain.User;
import com.springboot.dto.MzPatientXRayTask;
import com.springboot.dto.MzTasksDTO;
import com.springboot.dto.Pid;
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

    //退出登录
    @RequestMapping(value = "/loginOut")
    public Result userLoginOut(HttpServletRequest request) {
        return mzService.loginOut(request);
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

    //查询已经建立的的所有病历表
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
    public Result newMzPatientHistory(@Valid @RequestBody MzPatientHistory mzPatientHistory, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return mzService.insertMzPatientHistory(mzPatientHistory, request);
    }

    //新建病历和增加任务表一起
    @ResponseBody
    @PostMapping(value = "/newMzPatientAndXTask")
    public Result newMzPatientHistoryAndXTask(@Valid @RequestBody MzPatientXRayTask mzPatientXRayTask, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return mzService.insertMzPatientHistoryAndXTask(mzPatientXRayTask, request);
    }


    //修改病历表(根据id)
    @ResponseBody
    @PostMapping(value = "/updateMzPatient")
    public Result updateMzPatientHistory(@Valid @RequestBody MzPatientXRayTask mzPatientHistory, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return mzService.updateMzPatientHistoryById(mzPatientHistory, request);
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
        return mzService.insertMzXrayTask(mzXrayTask, request);
    }

    //修改胸片审查任务表（根据id）
    @ResponseBody
    @PostMapping(value = "/updateMzXrayTask")
    public Result updateMzXrayTask(@Valid @RequestBody MzXrayTask mzXrayTask, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return mzService.updateMzXrayTaskById(mzXrayTask, request);
    }

    //院外专家修改胸片审查任务表（根据id）
    @ResponseBody
    @PostMapping(value = "/updateMzXrayTaskOut")
    public Result updateMzXrayTaskOut(@Valid @RequestBody MzXrayTask mzXrayTask, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return mzService.updateMzXrayTaskOutById(mzXrayTask, request);
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
    public Result<List<MzTasksDTO>> findXRayTasks() {
        return mzService.selectMzXrayTasks();
    }

    //是否需要院外专家
    @ResponseBody
    @GetMapping(value = "/needExpert/{id}")
    public Result isNeedExpert(@PathVariable Integer id, HttpServletRequest request) {
        return mzService.isNeedExpert(id, request);
    }

    //需要院外专家处理的所有任务表
    @ResponseBody
    @GetMapping(value = "/MzOutExpertTasks")
    public Result<List<MzXrayTask>> selectAllMzExpertTasks(HttpServletRequest request) {
        return mzService.selectAllMzOutExpertTasks(request);
    }

    //院外专家提交处理信息（更新任务表）
    @ResponseBody
    @GetMapping(value = "/updateMzOutExpertTask")
    public Result updateOneMzOutExpertTask(MzXrayTask mzXrayTask, HttpServletRequest request) {
        return mzService.updateOneMzOutExpertTask(mzXrayTask, request);
    }

    //验证身份证号码是否重复
    @ResponseBody
    @PostMapping(value = "/checkPid")
    public Result checkPidRepeat(@RequestBody Pid pid) {
        return mzService.selectByPid(pid);
    }

    //病历表详细信息 + 对应任务表详情
    @ResponseBody
    @GetMapping(value = "/selectMzPatientAndXTask/{id}")
    public Result findPatientAndXTask(@PathVariable Integer id, HttpServletRequest request) {
        return mzService.selectMzPatientAndXTask(id, request);
    }

}