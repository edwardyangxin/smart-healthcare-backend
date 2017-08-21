package com.springboot.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.*;
import com.springboot.mapper.ServiceProviderMapper;
import com.springboot.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/7/12.
 */
@Slf4j
@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {
    @Value("${spring.mail.username}")
    private String username;
    private JavaMailSender javaMailSender;
    private ServiceProviderMapper serviceProviderMapper;

    @Autowired
    public ServiceProviderServiceImpl(ServiceProviderMapper serviceProviderMapper, JavaMailSender javaMailSender) {
        this.serviceProviderMapper = serviceProviderMapper;
        this.javaMailSender = javaMailSender;
    }
    @Override
    public String login(Login login, HttpSession session) {
        TpServiceProvider tpServiceProvider = serviceProviderMapper.selectByName(login.getName());
        Boolean status = tpServiceProvider.getStatus();
        String result;
        if (status == true) {
            if (tpServiceProvider != null) {
                if (!tpServiceProvider.getPassword().equals(login.getPassword())) {
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
        } else {
            result = "您的账户尚未激活。";
        }
        return result;
    }

    @Override
    public TpServiceProvider selectByName(String name) {
        return serviceProviderMapper.selectByName(name);
    }

    @Override
    public String insertServiceProvider(TpServiceProvider serviceProvider) {
        TpServiceProvider tpServiceProvider = serviceProviderMapper.selectByName(serviceProvider.getName());
        serviceProvider.setActiveCode(UUID.randomUUID().toString());
        serviceProvider.setStatus(false);
        if (tpServiceProvider == null) {
            serviceProviderMapper.insertServiceProvider(serviceProvider);
            return "注册成功！";
        } else {
            return "用户" + tpServiceProvider.getName() + "已存在！";
        }
    }

    @Override
    public String updateServiceProviderByName(ServiceProvider serviceProvider) {
        serviceProviderMapper.updateServiceProviderByName(serviceProvider);
        return "供应商信息更改成功！";
    }

    @Override
    public String updateServiceProviderPass(Password password, HttpSession session) {
        try {
            password.setName(session.getAttribute("name").toString());
        } catch (NullPointerException e) {
            log.info(e.toString());
            return "用户未登录。";
        }
        String TPPassword = serviceProviderMapper.selectByName(password.getName()).getPassword();
        if (password.getPassword().equals(TPPassword)) {
            if (password.getNewPassword().equals(password.getRetypePassword())) {
                if (TPPassword.equals(password.getNewPassword())) {
                    return "新密码与旧密码相同，请重新输入！";
                } else {
                    password.setPassword(password.getNewPassword());
                    serviceProviderMapper.updateServiceProviderPass(password);
                    session.removeAttribute("name");
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
    public String resetServiceProviderPass(ServiceProviderResetPass serviceProviderResetPass) {
        TpServiceProvider tpPersonal = serviceProviderMapper.selectByName(serviceProviderResetPass.getName());
        if (tpPersonal != null) {
            if (serviceProviderResetPass.getEmail().equals(tpPersonal.getEmail())) {
                if (serviceProviderResetPass.getTel().equals(tpPersonal.getTel())) {
                    serviceProviderMapper.resetPass(serviceProviderResetPass);
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
    public String newPersonInfo(TpPersonInfo tpPersonInfo,HttpSession session) {
        try {
            tpPersonInfo.setName(session.getAttribute("name").toString());
        } catch (Exception e) {
            log.info(e.toString());
            return "用户未登录。";
        }
        tpPersonInfo.setRegisterTime(new Date());
        serviceProviderMapper.newPersonInfo(tpPersonInfo);
        serviceProviderMapper.addIconAddress(tpPersonInfo);

        return "发布个人信息成功！";
    }

    @Override
    public String newEnterpriseInfo(TpEnterpriseProject tpEnterpriseProject,HttpSession session) {
        try {
            tpEnterpriseProject.setCompanyName(session.getAttribute("name").toString());
        } catch (Exception e) {
            log.info(e.toString());
            return "用户未登录。";
        }
        tpEnterpriseProject.setRegisterTime(new Date());
        tpEnterpriseProject.setServiceProvider(true);
        serviceProviderMapper.newEnterpriseInfo(tpEnterpriseProject);
        serviceProviderMapper.addEnterpriseIconAddress(tpEnterpriseProject);

        return "发布项目信息成功！";
    }


    @Override
    public List<TpPersonInfo> selectInfoLatest() {
        List<TpPersonInfo> tpPersonInfos;
        tpPersonInfos = serviceProviderMapper.selectInfoLatest();
        return tpPersonInfos;
    }

    @Override
    public List<TpEnterpriseProject> selectProjectLatest() {
        List<TpEnterpriseProject> tpEnterpriseProjects;
        tpEnterpriseProjects = serviceProviderMapper.selectProjectLatest();
        return tpEnterpriseProjects;
    }
    @Override
    public void sendMail(CheckMail checkMail) throws Exception {
        String name = checkMail.getName();
        TpServiceProvider tpServiceProvider = this.selectByName(name);
        String activeCode = tpServiceProvider.getActiveCode();
        String email = tpServiceProvider.getEmail();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(username);
        helper.setTo(email);
        helper.setSubject("主题：点击链接激活账户");
        StringBuffer sb = new StringBuffer();
        sb.append("<h1>请点击以下链接以激活账户：</h1>");
        sb.append("<a href=\"http://localhost:8080/translate/serviceProvider/emailCheck.action?name=");
        sb.append(name);
        sb.append("&activeCode=");
        sb.append(activeCode);
        sb.append("\">http://localhost:8080/translate/serviceProvider/emailCheck.action?name=");
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
        TpServiceProvider tpServiceProvider = this.selectByName(name);
        String tpName = tpServiceProvider.getName();
        String tpActiveCode = tpServiceProvider.getActiveCode();
        Boolean status = tpServiceProvider.getStatus();
        if (tpName != null && tpName.equals(name)) {
            if (activeCode.equals(tpActiveCode)) {
                if (status == false) {
                    tpServiceProvider.setStatus(true);
                    serviceProviderMapper.updateStatus(tpServiceProvider);
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
