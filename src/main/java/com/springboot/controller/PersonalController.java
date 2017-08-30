package com.springboot.controller;

import com.springboot.domain.Result;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.CheckMail;
import com.springboot.dto.Password;
import com.springboot.dto.PersonInfo;
import com.springboot.dto.PersonalResetPass;
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

    //根据amount的值查询个人最新发布的几条信息
    @RequestMapping(value = "/personal/latest")
    public Result<List<TpPersonInfo>> selectPersonInfoLatestAmount(@Param("amount") Integer amount) {
        return personalService.selectPersonInfoLatestAmount(amount);
    }

    //根据Name查询企业信息
    @GetMapping(value = "/personal/selectPersonal")
    public Result<TpPersonal> selectEnterprise(HttpSession session) {
        return personalService.selectPersonaByName(session);
    }


    //个人密码修改
    @PostMapping(value = "/personal/modifyPass")
    public Result<Password> modifyPass(@Valid @RequestBody Password password, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                Result result = new Result();
                result.setCode(300);
                result.setMsg(error.getDefaultMessage());
                return result;
            }
        }
        return personalService.updatePersonalPass(password, session);
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

    //完善个人信息
    @RequestMapping(value = "/personal/modifyPerson", method = RequestMethod.POST)
    public Result<TpPersonal> modifyPerson(@Valid @RequestBody TpPersonal tpPersonal, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                Result result = new Result();
                result.setCode(301);
                result.setMsg(error.getDefaultMessage());
                return result;
            }
        }
        return personalService.updatePersonByName(tpPersonal, session);
    }

    //发布个人信息、、接受Json格式的参数 Content-Type:application/json
    @PostMapping(value = "/personal/newInfo")
    public Result<TpPersonInfo> newInfo(@RequestBody TpPersonInfo tpPersonInfo, HttpSession session) {
        return personalService.newInfo(tpPersonInfo, session);
    }

    //修改已发布的信息
    @PostMapping(value = "/personal/modifyInfo")
    public Result<TpPersonInfo> updateInfoById(@RequestBody TpPersonInfo tpPersonInfo) {
        return personalService.updateInfo(tpPersonInfo);
    }

    //删除已发布的个人信息
    @RequestMapping(value = "/personal/delInfo")
    public Result<PersonInfo> delInfo(@RequestBody PersonInfo personInfo) {
        return personalService.delInfo(personInfo);
    }

    //查询个人发布的信息,可以单条件查询，也可以多条件组合查询
    @PostMapping(value = "/personal/selectInfo")
    public Result<TpPersonInfo> selectInfos(@RequestBody PersonInfo personInfo) {
        Result<TpPersonInfo> tpPersonInfos = personalService.selectInfos(personInfo);
        return tpPersonInfos;
    }


    //通过ID查询一条信息
    @PostMapping(value = "/personal/selectInfoById")
    public Result<TpPersonInfo> selectInfoById(@RequestBody PersonInfo personInfo) {
        return personalService.selectInfoById(personInfo);
    }


/*    //根据Name查询个人信息
    @GetMapping(value = "/enterprise/selectEnterprise")
    public TpEnterprise selectEnterprise(HttpSession session) {
        return enterpriseService.selectEnterpriseByName(session);
    }*/

    //发送激活账户邮件
    @PostMapping(value = "/personal/sendMail")
    public void sendMail(@RequestBody CheckMail checkMail) throws Exception {
        personalService.sendMail(checkMail);
    }

    //点击邮件链接激活账户
    @RequestMapping(value = "/personal/emailCheck")
    public String emailCheck(CheckMail checkMail) {
        return personalService.emailCheck(checkMail);
    }
}
