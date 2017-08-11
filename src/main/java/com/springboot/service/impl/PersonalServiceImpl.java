package com.springboot.service.impl;


import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.*;
import com.springboot.mapper.PersonalMapper;
import com.springboot.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/7/12.
 */

@Service
public class PersonalServiceImpl implements PersonalService {

    @Value("${spring.mail.username}")
    private String username;
    private JavaMailSender javaMailSender;
    private PersonalMapper personalMapper;

    @Autowired
    public PersonalServiceImpl(PersonalMapper personalMapper, JavaMailSender javaMailSender) {
        this.personalMapper = personalMapper;
        this.javaMailSender = javaMailSender;
    }


    @Override
    public String login(Login login, HttpSession session) {
        TpPersonal tpPersonal = personalMapper.selectByName(login.getName());
        Boolean status = tpPersonal.getStatus();
        String result;
        if (status == true) {
            if (tpPersonal != null) {
                if (!tpPersonal.getPassword().equals(login.getPassword())) {
                    result = "密码错误";
                } else {
                    result = "登录成功";
                }
            } else {
                result = "该个人用户不存在";
            }
            if (result.equals("登录成功")) {
                //添加用户信息到session中
                session.setAttribute("name", login.getName());
            }
        }else {
            result="您的账户尚未激活。";
        }
        return result;
    }

    @Override
    public TpPersonal selectByName(String name) {
        return personalMapper.selectByName(name);
    }

    @Override
    public String insertPerson(TpPersonal tpPersonal) {
        TpPersonal tpPersonal1 = personalMapper.selectByName(tpPersonal.getName());
        tpPersonal.setActiveCode(UUID.randomUUID().toString());
        tpPersonal.setStatus(false);
        if (tpPersonal1 == null) {
            personalMapper.insertPerson(tpPersonal);
            return "注册成功！";
        } else {
            return "用户" + tpPersonal1.getName() + "已存在！";
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
    public String delInfo(PersonInfo personInfo) {
        TpPersonInfo tpPersonInfo = personalMapper.selectInfoById(personInfo);
        if (tpPersonInfo != null) {
            personalMapper.delInfo(personInfo.getId());
            return "删除信息成功！";
        } else {
            return "这条信息不存在。";
        }
    }

    @Override
    public List<TpPersonInfo> selectInfos(PersonInfo personInfo) {
        List<TpPersonInfo> tpPersonInfos = personalMapper.selectInfos(personInfo);
        return tpPersonInfos;
    }

    @Override
    public TpPersonInfo selectInfoById(PersonInfo personInfo) {
        TpPersonInfo tpPersonInfo = personalMapper.selectInfoById(personInfo);
        int clickAmount = tpPersonInfo.getClickAmount() + 1;
        tpPersonInfo.setClickAmount(clickAmount);
        if (clickAmount < 50) {
            tpPersonInfo.setStars(0);
        } else if (50 <= clickAmount && clickAmount < 100) {
            tpPersonInfo.setStars(1);
        } else if (100 <= clickAmount && clickAmount < 200) {
            tpPersonInfo.setStars(2);
        } else if (200 <= clickAmount && clickAmount < 400) {
            tpPersonInfo.setStars(3);
        } else if (400 <= clickAmount && clickAmount < 700) {
            tpPersonInfo.setStars(4);
        } else if (700 <= clickAmount) {
            tpPersonInfo.setStars(5);
        }
        personalMapper.addClickAmount(tpPersonInfo);
        return tpPersonInfo;
    }

    @Override
    public List<TpPersonInfo> selectLatest() {
        List<TpPersonInfo> tpPersonInfos = personalMapper.selectLatest();
        return tpPersonInfos;
    }

    @Override
    public void sendMail(CheckMail checkMail) throws Exception {
        String name = checkMail.getName();
        TpPersonal tpPersonal = this.selectByName(name);
        String activeCode = tpPersonal.getActiveCode();
        String email = tpPersonal.getEmail();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(username);
        helper.setTo(email);
        helper.setSubject("主题：点击链接激活账户");
        StringBuffer sb = new StringBuffer();
        sb.append("<h1>请点击以下链接以激活账户：</h1>");
        sb.append("<a href=\"http://localhost:8080/translate/personal/emailCheck.action?name=");
        sb.append(name);
        sb.append("&activeCode=");
        sb.append(activeCode);
        sb.append("\">http://localhost:8080/translate/personal/emailCheck.action?name=");
        sb.append(name);
        sb.append("&activeCode=");
        sb.append(activeCode);
        sb.append("</a>" + "<br/><br/><br/><p style='color:#F00'>如果以上链接无法点击，请把上面网页地址复制到浏览器地址栏中打开!</p><br/><br/></div></div>");

        helper.setText(sb.toString(), true);

        javaMailSender.send(mimeMessage);
    }

    @Override
    public String emailCheck(CheckMail checkMail) {
        String name = checkMail.getName();
        String activeCode = checkMail.getActiveCode();
        TpPersonal tpPersonal = this.selectByName(name);
        String tpName = tpPersonal.getName();
        String tpActiveCode = tpPersonal.getActiveCode();
        Boolean status = tpPersonal.getStatus();
        if (tpName != null && tpName.equals(name)) {
            if (activeCode.equals(tpActiveCode)) {
                if (status == false) {
                    tpPersonal.setStatus(true);
                    personalMapper.updateStatus(tpPersonal);
                    return "激活成功！";
                } else {
                    return "已激活";
                }
            } else {
                return "激活码错误！";
            }
        }
        return "用户名错误！";
    }

}


