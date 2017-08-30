package com.springboot.controller;

import com.springboot.domain.Result;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.CheckMail;
import com.springboot.dto.Password;
import com.springboot.dto.ServiceProvider;
import com.springboot.dto.ServiceProviderResetPass;
import com.springboot.service.ServiceProviderService;
import org.apache.ibatis.annotations.Param;
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
public class ServiceProviderController {

    private ServiceProviderService serviceProviderService;

    @Autowired
    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    //根据amount的值 查询供应商最新发布的几条“个人信息”
    @RequestMapping(value = "/serviceProvider/person_latest")
    public Result<List<TpPersonInfo>> selectPersonInfoLatestAmount(@Param("amount") Integer amount) {
        return serviceProviderService.selectPersonInfoLatestAmount(amount);
    }

    //根据amount的值查询供应商最新发布的几条“企业信息”
    @RequestMapping(value = "/serviceProvider/enterprise_latest")
    public Result<List<TpEnterpriseProject>> selectEnterpriseInfoLatestAmount(@Param("amount") Integer amount) {
        return serviceProviderService.selectEnterpriseInfoLatestAmount(amount);
    }

    //根据Name查询供应商个人信息
    @GetMapping(value = "/serviceProvider/selecterviceProvider")
    public Result<TpServiceProvider> selectServiceProvider(HttpSession session) {
        return serviceProviderService.selectServiceProviderByName(session);
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
