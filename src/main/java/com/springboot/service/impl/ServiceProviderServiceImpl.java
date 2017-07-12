package com.springboot.service.impl;


import com.springboot.domain.TpServiceProvider;
import com.springboot.mapper.ServiceProviderMapper;
import com.springboot.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
