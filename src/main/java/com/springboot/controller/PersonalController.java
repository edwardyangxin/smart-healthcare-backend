package com.springboot.controller;

import com.springboot.domain.Result;
import com.springboot.domain.TpFile;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.*;
import com.springboot.service.PersonalService;
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
public class PersonalController {

    private PersonalService personalService;

    @Autowired
    public PersonalController(PersonalService personalService) {
        this.personalService = personalService;
    }

    //个人登陆
    @PostMapping(value = "/personal/login")
    public String personalLogin(@Valid @RequestBody Login login, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return personalService.login(login, session);
    }

    //个人注册
    @RequestMapping(value = "/personal/register", method = RequestMethod.POST)
    public String insertPerson(@Valid @RequestBody TpPersonal tpPersonal, TpFile tpFile, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return personalService.insertPerson(tpPersonal, tpFile);
    }

    //个人密码修改
    @PostMapping(value = "/personal/modifyPass")
    public String modifyPass(@Valid @RequestBody Password password, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        String result = personalService.updatePersonalPass(password, session);
        return result;
    }

    //个人重置密码（真实姓名、邮箱、电话）
    @PostMapping(value = "/personal/resetPass")
    public String resetPass(@Valid @RequestBody PersonalResetPass personalResetPass, BindingResult bindingResult) {
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
    @RequestMapping(value = "/personal/modifyPerson", method = RequestMethod.POST)
    public String modifyPerson(@Valid @RequestBody Personal person, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return personalService.updatePersonByName(person, session);
    }

    //发布个人信息、、接受Json格式的参数 Content-Type:application/json
    @PostMapping(value = "/personal/newInfo")
    public Result<TpPersonInfo> newInfo(@RequestBody TpPersonInfo tpPersonInfo, HttpSession session) {
        return personalService.newInfo(tpPersonInfo, session);
    }

    //修改已发布的信息
    @PostMapping(value = "/personal/modifyInfo")
    public void updateInfoById(@RequestBody TpPersonInfo tpPersonInfo) {
        personalService.updateInfo(tpPersonInfo);
    }

    //删除已发布的个人信息
    @RequestMapping(value = "/personal/delInfo")
    public String delInfo(@RequestBody PersonInfo personInfo) {
        return personalService.delInfo(personInfo);
    }

    //查询个人发布的信息,可以单条件查询，也可以多条件组合查询
    @PostMapping(value = "/personal/selectInfo")
    public List<TpPersonInfo> selectInfos(@RequestBody PersonInfo personInfo) {
        List<TpPersonInfo> tpPersonInfos = personalService.selectInfos(personInfo);
        return tpPersonInfos;
    }

    //通过ID查询一条信息
    @PostMapping(value = "/personal/selectInfoById")
    public TpPersonInfo selectInfoById(@RequestBody PersonInfo personInfo) {
        return personalService.selectInfoById(personInfo);
    }

    //查询最新五条信息
    @RequestMapping(value = "/personal/latest")
    public List<TpPersonInfo> selectLatestFive(@Param("amount") Integer amount) {
        List<TpPersonInfo> tpPersonInfos = personalService.selectLatest(amount);
        return tpPersonInfos;
    }

    //发送激活账户邮件
    @RequestMapping(value = "/personal/sendMail")
    public void sendMail(@RequestBody CheckMail checkMail) throws Exception {
        personalService.sendMail(checkMail);
    }

    //点击邮件链接激活账户
    @RequestMapping(value = "/personal/emailCheck")
    public String emailCheck(CheckMail checkMail) {
        return personalService.emailCheck(checkMail);
    }
}
