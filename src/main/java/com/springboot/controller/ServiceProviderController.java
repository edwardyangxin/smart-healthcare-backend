package com.springboot.controller;

import com.springboot.domain.TpServiceProvider;
import com.springboot.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/7/11.
 */
@Controller
public class ServiceProviderController {

    @Autowired
    private ServiceProviderService serviceProviderService;

    @ResponseBody
    @PostMapping(value = "/serviceprovider/login")
    public String enterpriseLogin(String name, String password, HttpSession session) {
        String result = serviceProviderService.login(name, password);
        if (result.equals("登录成功")) {
            //添加用户信息到session中
            TpServiceProvider tpServiceProvider = serviceProviderService.selectByName(name);
            session.setAttribute("name", name);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/registeServiceProvider", method = RequestMethod.POST)
    public String insertServiceProvider(TpServiceProvider serviceProvider) {
        return serviceProviderService.insertServiceProvider(serviceProvider);
    }
}
