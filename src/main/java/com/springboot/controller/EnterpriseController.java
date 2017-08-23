package com.springboot.controller;

import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.dto.*;
import com.springboot.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@RequestMapping(value = "/translate")
@RestController
public class EnterpriseController {

    private EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    //企业登录
    @PostMapping(value = "/enterprise/login")
    public String enterpriseLogin(@Valid @RequestBody Login login, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return enterpriseService.login(login, session);
    }

    //企业注册
    @RequestMapping(value = "/enterprise/register", method = RequestMethod.POST)
    public String insertEnterprise(@Valid @RequestBody TpEnterprise tpEnterprise, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return enterpriseService.insertEnterprise(tpEnterprise);
    }

    //企业密码修改
    @PostMapping(value = "/enterprise/modifyPass")
    public String modifyPass(@Valid @RequestBody Password password, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return enterpriseService.updateEnterprisePass(password, session);
    }

    //企业信息修改
    @RequestMapping(value = "/enterprise/modifyEnterprise", method = RequestMethod.POST)
    public String modifyEnterprise(@Valid @RequestBody TpEnterprise tpEnterprise, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return enterpriseService.updateEnterpriseByName(tpEnterprise, session);
    }

    //企业密码重置
    @PostMapping(value = "/enterprise/resetPass")
    public String resetPass(@Valid @RequestBody EnterpriseResetPass enterpriseResetPass, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return  enterpriseService.resetEnterprisePass(enterpriseResetPass);
    }

    //企业发布项目信息
    @PostMapping(value = "/enterprise/newProject")
    public String newProject(@RequestBody TpEnterpriseProject tpEnterpriseProject, HttpSession session) {
        return enterpriseService.newProject(tpEnterpriseProject, session);
    }

    //修改已发布的项目信息
    @RequestMapping(value = "/enterprise/modifyProject", method = RequestMethod.POST)
    public String modifyProject(@Valid @RequestBody TpEnterpriseProject tpEnterpriseProject, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return enterpriseService.updateEnterpriseProjectById(tpEnterpriseProject, session);
    }

    //删除已发布的项目信息
    @PostMapping(value = "/enterprise/delProject")
    public String delProject(@RequestBody EnterpriseProject enterpriseProject) {
        return enterpriseService.delProject(enterpriseProject);
    }

    //通过ID查询一条项目信息
    @PostMapping(value = "/enterprise/selectProjectById")
    public TpEnterpriseProject selectProjectById(@RequestBody EnterpriseProject enterpriseProject) {
        return enterpriseService.selectProjectById(enterpriseProject);
    }

    //查询企业发布的项目信息,可以单条件查询，也可以多条件组合查询
    @PostMapping(value = "/enterprise/selectProject")
    public List<TpEnterpriseProject> selectProjects(@RequestBody EnterpriseProject enterpriseProject) {
        List<TpEnterpriseProject> tpEnterpriseProjects = enterpriseService.selectProjects(enterpriseProject);
        return tpEnterpriseProjects;
    }

    //查询企业最新发布的四条信息
    @PostMapping(value = "/enterprise/latest")
    public List<TpEnterpriseProject> selectLatestFour() {
        List<TpEnterpriseProject> tpEnterpriseProjects = enterpriseService.selectLatest();
        return tpEnterpriseProjects;
    }

    //发送激活账户邮件
    @RequestMapping(value = "/enterprise/sendMail")
    public void sendMail(@RequestBody CheckMail checkMail) throws Exception {
        enterpriseService.sendMail(checkMail);
    }

    //点击邮件链接激活账户
    @RequestMapping(value = "/enterprise/emailCheck")
    public String emailCheck(CheckMail checkMail) {
        return enterpriseService.emailCheck(checkMail);
    }

    //根据Name查询企业信息
    @GetMapping(value = "/enterprise/selectEnterprise")
    public TpEnterprise selectEnterprise(HttpSession session) {
        return enterpriseService.selectEnterpriseByName(session);
    }
}
