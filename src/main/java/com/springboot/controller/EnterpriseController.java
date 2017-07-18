package com.springboot.controller;

import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.Enterprise;
import com.springboot.dto.Password;
import com.springboot.service.EnterpriseService;
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
public class EnterpriseController {

    private EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    //企业登录
    @ResponseBody
    @PostMapping(value = "/enterprise/login")
    public String enterpriseLogin(String name, String password, HttpSession session) {
        String result = enterpriseService.login(name, password);
        if (result.equals("登录成功")) {
            //添加用户信息到session中
            TpEnterprise tpEnterprise = enterpriseService.selectByName(name);
            session.setAttribute("city", tpEnterprise.getCity());
            session.setAttribute("name", name);
        }
        return result;
    }

    //企业注册
    @ResponseBody
    @RequestMapping(value = "/enterprise/register", method = RequestMethod.POST)
    public String insertEnterprise(@Valid TpEnterprise enterprise, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return enterpriseService.insertEnterprise(enterprise);
    }

    //企业密码修改
    @ResponseBody
    @PostMapping(value = "/enterprise/modifyPass")
    public String modifyPass(@Valid Password password, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        password.setName(session.getAttribute("name").toString());
        String result = enterpriseService.updateEnterprisePassByName(password);
        return result;
    }

    //企业信息修改
    @ResponseBody
    @RequestMapping(value = "/enterprise/modifyEnterprise", method = RequestMethod.POST)
    public String modifyEnterprise(@Valid Enterprise enterprise, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        enterprise.setName(session.getAttribute("name").toString());
        return enterpriseService.updateEnterpriseByName(enterprise);
    }

}
