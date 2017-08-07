package com.springboot.service.impl;

import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.dto.Enterprise;
import com.springboot.dto.EnterpriseResetPass;
import com.springboot.dto.Password;
import com.springboot.dto.SelectEnterpriseProject;
import com.springboot.mapper.EnterpriseMapper;
import com.springboot.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public String login(String name, String password) {
        //通过用户名获取用户
        TpEnterprise tpEnterprise = enterpriseMapper.selectByName(name);
        //若获取失败
        if (tpEnterprise == null) {
            return "该企业用户不存在";
        }
        //获取成功后，将获取用户的密码和传入密码对比
        else if (!tpEnterprise.getPassword().equals(password)) {
            return "密码错误";
        } else {
            return "登录成功";
        }
    }

    @Override
    public TpEnterprise selectByName(String name) {
        return enterpriseMapper.selectByName(name);
    }

    @Override
    public String insertEnterprise(TpEnterprise enterprise){
        TpEnterprise enterprise1 = enterpriseMapper.selectByName(enterprise.getName());
        if(enterprise1==null){
            enterpriseMapper.insertEnterprise(enterprise);
            return "注册成功！";
        }else{
            return "用户"+enterprise1.getName()+"已存在！";
        }
    }

    @Override
    public String updateEnterpriseByName(Enterprise enterprise) {
        enterpriseMapper.updateEnterpriseByName(enterprise);
        return "企业信息更改成功！";
    }


    @Override
    public String updateEnterprisePassByName(Password password) {
        String TPPassword = enterpriseMapper.selectByName(password.getName()).getPassword();
        if (password.getPassword().equals(TPPassword)) {
            if (password.getNewPassword().equals(password.getRetypePassword())) {
                if (TPPassword.equals(password.getNewPassword())) {
                    return "新密码与旧密码相同，请重新输入！";
                } else {
                    password.setPassword(password.getNewPassword());
                    enterpriseMapper.updateEnterprisePassByName(password);
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
    public String resetEnterprisePass(EnterpriseResetPass enterpriseResetPass){
        TpEnterprise tpEnterprise = enterpriseMapper.selectByName(enterpriseResetPass.getName());
        if(tpEnterprise != null){
            if(enterpriseResetPass.getTel().equals(tpEnterprise.getTel())){
                enterpriseMapper.resetPass(enterpriseResetPass);
                return "重置密码成功";
            }else {
                return "电话号码错误，请重试！";
            }
        }else {
            return "没有此用户！";
        }
    }

    @Override
    public String newProject(TpEnterpriseProject tpEnterpriseProject){
        enterpriseMapper.newProject(tpEnterpriseProject);
        return "发布企业信息成功";
    }

    @Override
    public List<TpEnterpriseProject> selectProjects(SelectEnterpriseProject selectEnterpriseProject){
        List<TpEnterpriseProject> tpEnterpriseProjects = enterpriseMapper.selectProjects(selectEnterpriseProject);
        return tpEnterpriseProjects;
    }

    @Override
    public List<TpEnterpriseProject> selectLatest(){
        List<TpEnterpriseProject> tpEnterpriseProjects = enterpriseMapper.selectLatest();
        return tpEnterpriseProjects;
    }

}
