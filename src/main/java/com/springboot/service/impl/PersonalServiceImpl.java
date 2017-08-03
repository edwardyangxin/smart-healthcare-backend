package com.springboot.service.impl;


import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.*;
import com.springboot.mapper.PersonalMapper;
import com.springboot.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

@Service
public class PersonalServiceImpl implements PersonalService {
    @Autowired
    private PersonalMapper personalMapper;

    @Override
    public String login(Login login) {
        //通过用户名获取用户
        TpPersonal tpPersonal = personalMapper.selectByName(login.getName());
        //若获取失败
        if (tpPersonal == null) {
            return "该个人用户不存在";
        }
        //获取成功后，将获取用户的密码和传入密码对比
        else if (!tpPersonal.getPassword().equals(login.getPassword())) {
            return "密码错误";
        } else {
            return "登录成功";
        }
    }

    @Override
    public TpPersonal selectByName(String name) {
        return personalMapper.selectByName(name);
    }

    @Override
    public String insertPerson(TpPersonal person) {
        TpPersonal person1 = personalMapper.selectByName(person.getName());
        if (person1 == null) {
            personalMapper.insertPerson(person);
            return "注册成功！";
        } else {
            return "用户" + person1.getName() + "已存在！";
        }
    }

    @Override
    public String updatePersonalPass(Password password) {
        String TPPassword = personalMapper.selectByName(password.getName()).getPassword();
        if (password.getPassword().equals(TPPassword)) {
            if (password.getNewPassword().equals(password.getRetypePassword())) {
                if (TPPassword.equals(password.getNewPassword())) {
                    return "新密码与旧密码相同，请重新输入！";
                } else {
                    password.setPassword(password.getNewPassword());
                    personalMapper.updatePassword(password);
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
    public String resetPersonalPass(PersonalResetPass personalResetPass) {
        TpPersonal tpPersonal = personalMapper.selectByRealName(personalResetPass.getRealName());
        if (tpPersonal != null) {
            if (personalResetPass.getEmail().equals(tpPersonal.getEmail())) {
                if (personalResetPass.getTel().equals(tpPersonal.getTel())) {
                    personalMapper.resetPass(personalResetPass);
                    return "重置密码成功。";
                } else {
                    return "电话号码错误，请重试！";
                }
            } else {
                return "Email错误，请重试！";
            }
        } else {
            return "没有此用户！";
        }
    }

    @Override
    public String updatePersonByName(Personal person) {
        personalMapper.updatePersonByName(person);
        return "个人信息更改成功！";
    }

    @Override
    public String newInfo(TpPersonInfo tpPersonInfo) {
        personalMapper.newInfo(tpPersonInfo);
        return "发布个人信息成功！";
    }

    @Override
    public void delInfo(Integer id) {
        personalMapper.delInfo(id);
    }

    @Override
    public List<TpPersonInfo> selectInfos(SelectPersonInfo selectPersonInfo) {
        List<TpPersonInfo> tpPersonInfos = personalMapper.selectInfos(selectPersonInfo);
        return tpPersonInfos;
    }

    @Override
    public TpPersonInfo selectInfoById(SelectPersonInfo selectPersonInfo) {
        TpPersonInfo tpPersonInfo=personalMapper.selectInfoById(selectPersonInfo);
        return tpPersonInfo;
    }

    @Override
    public List<TpPersonInfo> selectLatest() {
        List<TpPersonInfo> tpPersonInfos = personalMapper.selectLatest();
        return tpPersonInfos;
    }

}


