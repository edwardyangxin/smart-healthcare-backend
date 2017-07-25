package com.springboot.controller;

import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.Password;
import com.springboot.dto.ServiceProvider;
import com.springboot.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Controller
public class ServiceProviderController {

    private ServiceProviderService serviceProviderService;

    @Autowired
    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    //供应商登录
    @ResponseBody
    @PostMapping(value = "/serviceProvider/login")
    public String serviceProviderLogin(String name, String password, HttpSession session) {
        String result = serviceProviderService.login(name, password);
        if (result.equals("登录成功")) {
            //添加用户信息到session中
            TpServiceProvider tpServiceProvider = serviceProviderService.selectByName(name);
            session.setAttribute("name", name);
        }
        return result;
    }
    //供应商注册
    @ResponseBody
    @RequestMapping(value = "/serviceProvider/register", method = RequestMethod.POST)
    public String insertServiceProvider(@Valid TpServiceProvider serviceProvider, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
       return serviceProviderService.insertServiceProvider(serviceProvider);
    }
    //供应商信息修改
    @ResponseBody
    @RequestMapping(value = "/serviceProvider/modifyerviceProvider", method = RequestMethod.POST)
    public String   modifyEnterprise(@Valid ServiceProvider serviceProvider, BindingResult bindingResult, HttpSession session){
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        serviceProvider.setName(session.getAttribute("name").toString());
        return serviceProviderService.updateServiceProviderByName(serviceProvider);
    }

    //供应商密码修改
    @ResponseBody
    @PostMapping(value = "/serviceProvider/modifyPass")
    public String modifyPass(@Valid Password password, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        password.setName(session.getAttribute("name").toString());
        String result = serviceProviderService.updateServiceProviderPassByName(password);

        return result;
    }
}
