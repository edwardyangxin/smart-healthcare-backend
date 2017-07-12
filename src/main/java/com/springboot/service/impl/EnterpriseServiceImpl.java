package com.springboot.service.impl;

import com.springboot.domain.TpEnterprise;
import com.springboot.mapper.EnterpriseMapper;
import com.springboot.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/12.
 */
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    EnterpriseMapper enterpriseMapper;

    @Override
    public String login(String name, String password) {
        //通过用户名获取用户
        TpEnterprise tpEnterprise = enterpriseMapper.selectByName(name);
        //若获取失败
        if (tpEnterprise == null) {
            return "该用户不存在";
        }
        //获取成功后，将获取用户的密码和传入密码对比
        else if (!tpEnterprise.getPassword().equals(password)) {
            return "密码错误";
        } else {
            return "登陆成功";
        }
    }

    @Override
    public TpEnterprise selectByName(String name) {
        return enterpriseMapper.selectByName(name);
    }

}
