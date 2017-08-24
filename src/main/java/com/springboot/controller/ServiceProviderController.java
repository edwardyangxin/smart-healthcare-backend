package com.springboot.controller;

import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.*;
import com.springboot.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@RequestMapping(value = "/translate")
@RestController
public class ServiceProviderController {

    private ServiceProviderService serviceProviderService;

    @Autowired
    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    //供应商登录
    @PostMapping(value = "/serviceProvider/login")
    public String serviceProviderLogin(@Valid @RequestBody Login login, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return serviceProviderService.login(login, session);
    }

    //供应商注册
    @RequestMapping(value = "/serviceProvider/register", method = RequestMethod.POST)
    public String insertServiceProvider(@Valid @RequestBody TpServiceProvider serviceProvider, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return serviceProviderService.insertServiceProvider(serviceProvider);
    }

    //供应商密码修改，需登录后，传入name.password,newpassword,retypePassword
    @PostMapping(value = "/serviceProvider/modifyPass")
    public String modifyPass(@Valid @RequestBody Password password, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        String result = serviceProviderService.updateServiceProviderPass(password,session);
        return result;
    }

    //供应商重置密码（真实姓名、邮箱、电话）,需传入name,tel,email,newPassword
    @PostMapping(value = "/serviceProvider/resetPass")
    public String resetPass(@Valid @RequestBody ServiceProviderResetPass serviceProviderResetPass, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        String result = serviceProviderService.resetServiceProviderPass(serviceProviderResetPass);
        return result;
    }

    //发布供应商信息(个人)
    @PostMapping(value = "/serviceProvider/newPersonInfo")
    public String newPersonInfo(@RequestBody TpPersonInfo tpPersonInfo, HttpSession session) {
        return serviceProviderService.newPersonInfo(tpPersonInfo,session);
    }
    //发布供应商信息(企业)
    @PostMapping(value = "/serviceProvider/newEnterpriseInfo")
    public String newEnterpriseInfo(@RequestBody TpEnterpriseProject tpEnterpriseProject, HttpSession session) {
        return serviceProviderService.newEnterpriseInfo(tpEnterpriseProject,session);
    }
    //供应商信息修改
    @RequestMapping(value = "/serviceProvider/modifyServiceProvider", method = RequestMethod.POST)
    public String modifyServiceProvider(@Valid @RequestBody ServiceProvider serviceProvider, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        serviceProvider.setName(session.getAttribute("name").toString());
        return serviceProviderService.updateServiceProviderByName(serviceProvider);
    }

    //查询最新四条信息
    @RequestMapping(value = "/serviceProvider/infolatest")
    public List<TpPersonInfo> selectInfoLatestFive() {
        List<TpPersonInfo> tpPersonInfos = serviceProviderService.selectInfoLatest();
        return tpPersonInfos;
    }
    //查询最新四条项目信息
    @RequestMapping(value = "/serviceProvider/projectlatest")
    public List<TpEnterpriseProject> selectProjectLatestFive() {
        List<TpEnterpriseProject> tpEnterpriseProjects = serviceProviderService.selectProjectLatest();
        return tpEnterpriseProjects;
    }

    //发送激活账户邮件
    @RequestMapping(value = "/serviceProvider/sendMail")
    public void sendMail(@RequestBody CheckMail checkMail) throws Exception {
        serviceProviderService.sendMail(checkMail);
    }

    //点击邮件链接激活账户
    @RequestMapping(value = "/serviceProvider/emailCheck")
    public String emailCheck(CheckMail checkMail) {
        return serviceProviderService.emailCheck(checkMail);
    }
}
