package com.springboot.service.impl;


import com.springboot.domain.TpServiceProvider;
import com.springboot.domain.TpServiceProviderInfo;
import com.springboot.dto.Password;
import com.springboot.dto.SelectServiceProviderInfo;
import com.springboot.dto.ServiceProvider;
import com.springboot.mapper.ServiceProviderMapper;
import com.springboot.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {
    @Autowired
    private ServiceProviderMapper serviceProviderMapper;

    @Override
    public String login(String name, String password) {
        //通过用户名获取用户
        TpServiceProvider tpServiceProvider = serviceProviderMapper.selectByName(name);
        //若获取失败
        if (tpServiceProvider == null) {
            return "该服务供应商不存在";
        }
        //获取成功后，将获取用户的密码和传入密码对比
        else if (!tpServiceProvider.getPassword().equals(password)) {
            return "密码错误";
        }else {
            return "登录成功";
        }
    }

    @Override
    public TpServiceProvider selectByName(String name) {
        return serviceProviderMapper.selectByName(name);
    }

    @Override
    public String insertServiceProvider(TpServiceProvider serviceProvider){
        TpServiceProvider serviceProvider1 = serviceProviderMapper.selectByName(serviceProvider.getName());
        if(serviceProvider1==null){
            serviceProviderMapper.insertServiceProvider(serviceProvider);
            return "注册成功！";
        }else{
            return "用户"+serviceProvider1.getName()+"已存在！";
        }
    }

    @Override
    public String updateServiceProviderByName(ServiceProvider serviceProvider) {
        serviceProviderMapper.updateServiceProviderByName(serviceProvider);
        return "供应商信息更改成功！";
    }
    @Override
    public String updateServiceProviderPassByName(Password password) {
        String TPPassword = serviceProviderMapper.selectByName(password.getName()).getPassword();
        if (password.getPassword().equals(TPPassword)) {
            if (password.getNewPassword().equals(password.getRetypePassword())) {
                if (TPPassword.equals(password.getNewPassword())) {
                    return "新密码与旧密码相同，请重新输入！";
                } else {
                    password.setPassword(password.getNewPassword());
                    serviceProviderMapper.updateServiceProviderPassByName(password);
                    return "密码修改成功！";
                }
            } else {
                return "两次输入的新密码不同，请重试！";
            }
        } else {
            return "旧密码输入错误，请重试！";
        }
    }
    @Override
    public String newInfo(TpServiceProviderInfo tpServiceProviderInfo) {
        serviceProviderMapper.newInfo(tpServiceProviderInfo);
        return "发布信息成功！";
    }

    @Override
    public List<TpServiceProviderInfo> selectInfos(SelectServiceProviderInfo selectServiceProviderInfo) {
        List<TpServiceProviderInfo> tpServiceProviderInfos;
        tpServiceProviderInfos = serviceProviderMapper.selectInfos(selectServiceProviderInfo);
        return tpServiceProviderInfos;
    }

    @Override
    public List<TpServiceProviderInfo> selectLatest() {
        List<TpServiceProviderInfo> tpServiceProviderInfos;
        tpServiceProviderInfos = serviceProviderMapper.selectLatest();
        return tpServiceProviderInfos;
    }
}
