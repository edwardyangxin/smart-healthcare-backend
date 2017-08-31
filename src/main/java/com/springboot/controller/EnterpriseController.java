package com.springboot.controller;

import com.springboot.domain.Result;
import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.dto.CheckMail;
import com.springboot.dto.EnterpriseProject;
import com.springboot.dto.EnterpriseResetPass;
import com.springboot.dto.Password;
import com.springboot.service.EnterpriseService;
import com.springboot.tools.ResultUtil;
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
public class EnterpriseController {

    private EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    //根据amount的值查询企业最新发布的几条信息
    @ResponseBody
    @RequestMapping(value = "/enterprise/latest")
    public Result<List<TpEnterpriseProject>> selectEnterpriseInfoLatestAmount(@Param("amount") Integer amount) {
        return enterpriseService.selectEnterpriseInfoLatestAmount(amount);
    }

    //根据UUID查询企业自身信息
    @ResponseBody
    @GetMapping(value = "/enterprise/selectEnterprise")
    public Result<TpEnterprise> selectEnterprise(HttpSession session) {
        return enterpriseService.selectEnterpriseByName(session);
    }

    //完善修改企业信息
    @ResponseBody
    @RequestMapping(value = "/enterprise/modifyEnterprise", method = RequestMethod.POST)
    public Result  modifyEnterprise(@Valid @RequestBody TpEnterprise tpEnterprise, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return enterpriseService.updateEnterpriseByName(tpEnterprise, session);
    }

    //企业发布项目信息
    @ResponseBody
    @PostMapping(value = "/enterprise/newProject")
    public Result newEnterpriseProject(@Valid @RequestBody TpEnterpriseProject tpEnterpriseProject, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return enterpriseService.newEnterpriseProject(tpEnterpriseProject, session);
    }

    //修改企业已发布的项目信息
    @ResponseBody
    @RequestMapping(value = "/enterprise/modifyProject", method = RequestMethod.POST)
    public Result updateEnterpriseProjectById(@Valid @RequestBody TpEnterpriseProject tpEnterpriseProject, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return enterpriseService.updateEnterpriseProjectById(tpEnterpriseProject, session);
    }

























    //删除已发布的项目信息
    @PostMapping(value = "/enterprise/delProject")
    public String delProject(@RequestBody EnterpriseProject enterpriseProject) {
        return enterpriseService.delProject(enterpriseProject);
    }

    //通过ID查询一条项目信息
    @PostMapping(value = "/enterprise/selectProjectById")
    public TpEnterpriseProject selectProjectById(@RequestBody EnterpriseProject enterpriseProject) {
        return enterpriseService.selectProjectById(enterpriseProject);
    }

    //查询企业发布的项目信息,可以单条件查询，也可以多条件组合查询
    @PostMapping(value = "/enterprise/selectProject")
    public List<TpEnterpriseProject> selectProjects(@RequestBody EnterpriseProject enterpriseProject) {
        List<TpEnterpriseProject> tpEnterpriseProjects = enterpriseService.selectProjects(enterpriseProject);
        return tpEnterpriseProjects;
    }























    //发送激活账户邮件
    @PostMapping(value = "/enterprise/sendMail")
    public void sendMail(@RequestBody CheckMail checkMail) throws Exception {
        enterpriseService.sendMail(checkMail);
    }

    //点击邮件链接激活账户
    @RequestMapping(value = "/enterprise/emailCheck")
    public String emailCheck(CheckMail checkMail) {
        return enterpriseService.emailCheck(checkMail);
    }

    //企业密码修改
    @PostMapping(value = "/enterprise/modifyPass")
    public String modifyPass(@Valid @RequestBody Password password, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return enterpriseService.updateEnterprisePass(password, session);
    }



    //企业密码重置
    @PostMapping(value = "/enterprise/resetPass")
    public String resetPass(@Valid @RequestBody EnterpriseResetPass enterpriseResetPass, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return  enterpriseService.resetEnterprisePass(enterpriseResetPass);
    }


}
