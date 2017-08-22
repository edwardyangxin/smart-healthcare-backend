package com.springboot.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Constants;
import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpFile;
import com.springboot.domain.TpPersonal;
import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.Register;
import com.springboot.mapper.EnterpriseMapper;
import com.springboot.mapper.PersonalMapper;
import com.springboot.mapper.ServiceProviderMapper;
import com.springboot.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by Administrator on 2017/7/12.
 */

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
    public String insertPerson(Register register, TpFile tpFile, HttpServletRequest request, HttpServletResponse response) {

        register.setActiveCode(UUID.randomUUID().toString().replaceAll("-", ""));
        register.setStatus(false);
        tpFile.setName(register.getName());
        tpFile.setUuid(register.getActiveCode());
        tpFile.setPicturePath("http://localhost:8080/translate/pictures/default.jpg");
        String category = register.getCategory();
        String registerName = register.getName();
        String clientCode=register.getClientCode();

        HttpSession session = request.getSession();
        String serverCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        if (clientCode != null && clientCode.equalsIgnoreCase(serverCode)) {
            if (category.equals("personal")) {
                TpPersonal tpPersonal = personalMapper.selectByName(registerName);
                if (tpPersonal == null) {
                    personalMapper.newPerson(register);
                    personalMapper.newTpFile(tpFile);
                    return "注册成功！";
                } else {
                    return "用户" + tpPersonal.getName() + "已存在！";
                }
            } else if (category.equals("enterprise")) {
                TpEnterprise tpEnterprise = enterpriseMapper.selectByName(registerName);
                if (tpEnterprise == null) {
                    enterpriseMapper.newEnterprise(register);
                    personalMapper.newTpFile(tpFile);
                    return "注册成功！";
                } else {
                    return "用户" + tpEnterprise.getName() + "已存在！";
                }
            } else if (category.equals("serviceProvider")) {
                TpServiceProvider tpServiceProvider = serviceProviderMapper.selectByName(registerName);
                if (tpServiceProvider == null) {
                    serviceProviderMapper.newServiceProvider(register);
                    personalMapper.newTpFile(tpFile);
                    return "注册成功！";
                } else {
                    return "用户" + tpServiceProvider.getName() + "已存在！";
                }
            } else {
                return "请选择用户类别。";
            }
        } else {
            return "Kaptcha_error";
        }


    }

}
