package com.springboot.controller;

import com.springboot.domain.TpPersonal;
import com.springboot.dto.Personal;
import com.springboot.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Controller
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @ResponseBody
    @PostMapping(value = "/personal/login")
    public String enterpriseLogin(String name, String password, HttpSession session) {
        String result = personalService.login(name, password);
        if (result.equals("登录成功")) {
            //添加用户信息到session中
            TpPersonal tpPersonal = personalService.selectByName(name);
            session.setAttribute("name", name);
        }
        return result;
    }

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

    @ResponseBody
    @PostMapping(value = "/personal/modifyPass")
    public String modifyPass(HttpSession session, String password, String newPassword, String retypePassword, TpPersonal tpPersonal) {
        String name=session.getAttribute("name").toString();
        String result = personalService.updatePersonalPass(name, password, newPassword, retypePassword, tpPersonal);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/personal/modifyPerson",method = RequestMethod.POST)
    public  String modifyPerson(@Valid Personal person, BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList){
                return error.getDefaultMessage();
            }
        }
        person.setName(session.getAttribute("name").toString());
        return  personalService.updatePersonByName(person);
    }
}
