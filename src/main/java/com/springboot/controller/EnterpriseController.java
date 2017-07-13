package com.springboot.controller;

import com.springboot.domain.TpEnterprise;
import com.springboot.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/7/11.
 */
@Controller
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

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

    @ResponseBody
    @RequestMapping(value = "/enterprise/register", method = RequestMethod.POST)
    public String insertEnterprise(TpEnterprise enterprise) {
        return  enterpriseService.insertEnterprise(enterprise);
    }
}
