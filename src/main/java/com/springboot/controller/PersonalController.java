package com.springboot.controller;

import com.springboot.domain.TpPersonal;
import com.springboot.dto.Password;
import com.springboot.dto.Personal;
import com.springboot.dto.PersonalResetPass;
import com.springboot.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Controller
public class PersonalController {

    private PersonalService personalService;


    @Autowired
    public PersonalController(PersonalService personalService) {
        this.personalService = personalService;
    }

    //个人登陆
    @ResponseBody
    @PostMapping(value = "/personal/login")
    public String personalLogin(String name, String password, HttpSession session) {
        String result = personalService.login(name, password);
        if (result.equals("登录成功")) {
            //添加用户信息到session中
            TpPersonal tpPersonal = personalService.selectByName(name);
            session.setAttribute("name", name);
        }
        return result;
    }

    //个人注册
    @ResponseBody
    @RequestMapping(value = "/personal/register", method = RequestMethod.POST)
    public String insertPerson(@Valid TpPersonal person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return personalService.insertPerson(person);
    }

    //个人密码修改
    @ResponseBody
    @PostMapping(value = "/personal/modifyPass")
    public String modifyPass(@Valid Password password, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        password.setName(session.getAttribute("name").toString());
        String result = personalService.updatePersonalPass(password);
        return result;
    }

    //重置密码（真实姓名、邮箱、电话）
    @ResponseBody
    @PostMapping(value = "/personal/resetPass")
    public String resetPass(@Valid PersonalResetPass personalResetPass, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        String result = personalService.resetPersonalPass(personalResetPass);
        return result;
    }

    //个人信息修改
    @ResponseBody
    @RequestMapping(value = "/personal/modifyPerson", method = RequestMethod.POST)
    public String modifyPerson(@Valid Personal person, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        person.setName(session.getAttribute("name").toString());
        return personalService.updatePersonByName(person);
    }
}
