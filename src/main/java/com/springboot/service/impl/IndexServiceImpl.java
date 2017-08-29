package com.springboot.service.impl;


import com.google.code.kaptcha.Constants;
import com.springboot.domain.*;
import com.springboot.dto.Login;
import com.springboot.dto.LoginReturn;
import com.springboot.dto.Register;
import com.springboot.enums.ResultEnum;
import com.springboot.mapper.EnterpriseMapper;
import com.springboot.mapper.PersonalMapper;
import com.springboot.mapper.ServiceProviderMapper;
import com.springboot.service.IndexService;
import com.springboot.tools.ResultUtil;
import com.springboot.tools.UUIDTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/7/12.
 */
@Slf4j
@Service
public class IndexServiceImpl implements IndexService {

    private PersonalMapper personalMapper;
    private EnterpriseMapper enterpriseMapper;
    private ServiceProviderMapper serviceProviderMapper;

    @Autowired
    public IndexServiceImpl(PersonalMapper personalMapper, EnterpriseMapper enterpriseMapper, ServiceProviderMapper serviceProviderMapper) {
        this.personalMapper = personalMapper;
        this.enterpriseMapper = enterpriseMapper;
        this.serviceProviderMapper = serviceProviderMapper;
    }

    @Override
    public String logout(HttpSession session) {
        session.removeAttribute("name");
        return "redirect:/index";
    }

    @Override
    public Result insertUser(Register register, HttpServletRequest request, HttpServletResponse response) {

        register.setActiveCode(UUIDTool.getUuid());
        register.setStatus(false);

        TpFile tpFile = new TpFile();
        tpFile.setName(register.getName());
        tpFile.setUuid(register.getActiveCode());
        tpFile.setPicturePath("http://localhost:8080/translate/pictures/default.jpg");

        String category = register.getCategory();
        String registerName = register.getName();
        String clientCode = register.getClientCode();

        HttpSession session = request.getSession();
        try {
            String serverCode = session.getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
            if (clientCode == null || !clientCode.equalsIgnoreCase(serverCode)) {
                log.info("输入的验证码为空，或者不正确！");
                return ResultUtil.error(ResultEnum.VERIFICATION_CODE_ERROE);
            }
        } catch (NullPointerException e) {
            log.info("Session中的验证码为空！");
            return ResultUtil.error(ResultEnum.NullPointerException);
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        if(category==null){
            return ResultUtil.error(ResultEnum.NullPointerException);
        }
        switch (category) {
            case "personal":
                return insertPersonal(registerName, register, tpFile);
            case "enterprise":
                return insertEnterprise(registerName, register, tpFile);
            case "serviceProvider":
                return insertServiceProvider(registerName, register, tpFile);
            default:
                log.info("匹配用户类型时出现错误！");
                return ResultUtil.error(ResultEnum.MATCHING_USER_TYPE_ERRPR);
        }
    }

    @Override
    public Result login(Login login, HttpServletRequest request) {
        String category = login.getCategory();
        if(category==null){
            return ResultUtil.error(ResultEnum.NullPointerException);
        }
        String name = login.getName();
        HttpSession session = request.getSession();
     /*   if (session.getAttribute("uuid").toString().length() == 0) {
        } else {
            return ResultUtil.error(ResultEnum.Repeat_login_Error);
        }*/
        switch (category) {
            case "personal":
                LoginReturn loginReturnPersonal = personalMapper.selectByName(name);
                return userLogin(loginReturnPersonal, login, session);
            case "enterprise":
                LoginReturn loginReturnEnterprise = enterpriseMapper.selectByName(name);
                return userLogin(loginReturnEnterprise, login, session);
            case "serviceProvider":
                LoginReturn loginReturnServiceProvider = serviceProviderMapper.selectByName(name);
                return userLogin(loginReturnServiceProvider, login, session);
            default:
                log.info("匹配用户类型时出现错误！");
                return ResultUtil.error(ResultEnum.MATCHING_USER_TYPE_ERRPR);
        }
    }

    public Result userLogin(LoginReturn loginReturn, Login login, HttpSession session) {
        if (loginReturn == null) {
            log.info("用户"+login.getName()+"不存在！");
            return ResultUtil.error(ResultEnum.NOT_EXIST_ERROR);
        } else {
            if (loginReturn.getStatus()== true) {
                if (loginReturn.getPassword().equals(login.getPassword())) {
                    session.setAttribute("name", login.getName());
                    session.setAttribute("uuid", loginReturn.getUuid());
                    log.info("用户"+login.getName()+"登陆成功！");
                    return ResultUtil.success(ResultEnum.LOGIN_SUCCESS);
                } else {
                    log.info("用户"+login.getName()+"密码输入错误！");
                    return ResultUtil.error(ResultEnum.PASSWORD_ERROR);
                }
            } else {
                log.info("用户"+login.getName()+"没有激活邮箱！");
                return ResultUtil.error(ResultEnum.NOT_ACTIVE_ERROR);
            }
        }
    }

    public Result insertPersonal(String registerName, Register register, TpFile tpFile) {
        LoginReturn tpPersonal = personalMapper.selectByName(registerName);
        if (tpPersonal == null) {
            personalMapper.newPerson(register);
            personalMapper.newTpFile(tpFile);
            log.info("个人用户" + registerName + "注册成功！");
            return ResultUtil.success(ResultEnum.REGISTER_SUCCESS);
        } else {
            log.info("个人用户" + registerName + "已存在！");
            return ResultUtil.error(ResultEnum.EXIST_ERROR);
        }
    }

    public Result insertEnterprise(String registerName, Register register, TpFile tpFile) {
        LoginReturn tpEnterprise = enterpriseMapper.selectByName(registerName);
        if (tpEnterprise == null) {
            enterpriseMapper.newEnterprise(register);
            personalMapper.newTpFile(tpFile);
            log.info("企业用户" + registerName + "注册成功！");
            return ResultUtil.success(ResultEnum.REGISTER_SUCCESS);
        } else {
            log.info("企业用户" + registerName + "已存在！");
            return ResultUtil.error(ResultEnum.EXIST_ERROR);
        }
    }

    public Result insertServiceProvider(String registerName, Register register, TpFile tpFile) {
        LoginReturn tpServiceProvider = serviceProviderMapper.selectByName(registerName);
        if (tpServiceProvider == null) {
            serviceProviderMapper.newServiceProvider(register);
            personalMapper.newTpFile(tpFile);
            log.info("供应商用户" + registerName + "注册成功！");
            return ResultUtil.success(ResultEnum.REGISTER_SUCCESS);
        } else {
            log.info("供应商" + registerName + "已存在！");
            return ResultUtil.error(ResultEnum.EXIST_ERROR);
        }
    }
}
