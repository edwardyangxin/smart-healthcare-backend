package com.springboot.service.impl;

import com.springboot.domain.Result;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.CheckMail;
import com.springboot.dto.Password;
import com.springboot.dto.ServiceProviderResetPass;
import com.springboot.enums.ResultEnum;
import com.springboot.mapper.ServiceProviderMapper;
import com.springboot.service.ServiceProviderService;
import com.springboot.tools.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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
    public Result<TpServiceProvider> selectServiceProviderByName(HttpSession session) {
        try {
            String uuid = session.getAttribute("serviceProviderUuid").toString();
            TpServiceProvider tpServiceProvider = serviceProviderMapper.selectServiceProviderByName(uuid);
            return ResultUtil.success(tpServiceProvider);
        } catch (Exception e) {
            log.info("未登录" + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
    }

    @Override
    public Result<List<TpPersonInfo>> selectPersonInfoLatestAmount(Integer amount) {
        List<TpPersonInfo> tpPersonInfos = serviceProviderMapper.selectPersonInfoLatestAmount(amount);
        log.info("查询了供应商最新发布的" + amount + "条个人信息！");
        return ResultUtil.success(tpPersonInfos);
    }

    @Override
    public Result<List<TpEnterpriseProject>> selectEnterpriseInfoLatestAmount(Integer amount) {
        List<TpEnterpriseProject> tpEnterpriseProjects = serviceProviderMapper.selectEnterpriseInfoLatestAmount(amount);
        log.info("查询了供应商最新发布的" + amount + "条企业信息！");
        return ResultUtil.success(tpEnterpriseProjects);
    }


    @Override
    public Result updateServiceProviderByName(TpServiceProvider tpServiceProvider, HttpSession session) {
        try {
            String uuid = session.getAttribute("serviceProviderUuid").toString();
            tpServiceProvider.setUuid(uuid);
            serviceProviderMapper.updateServiceProviderByName(tpServiceProvider);
            log.info(uuid + "修改供应商资料成功！");
            return ResultUtil.success(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.info("修改供应商资料时，用户未登录" + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
    }

    @Override
    public Result newServiceProviderPersonInfo(TpPersonInfo tpPersonInfo, HttpSession session) {
        try {
            String name = session.getAttribute("serviceProviderName").toString();
            String uuid = session.getAttribute("serviceProviderUuid").toString();
            tpPersonInfo.setName(name);
            tpPersonInfo.setUuid(uuid);
            tpPersonInfo.setRegisterTime(new Date());
            tpPersonInfo.setServiceProvider(true);
            serviceProviderMapper.newPersonInfo(tpPersonInfo);
            serviceProviderMapper.addIconAddress(tpPersonInfo);
            log.info("供应商用户:" + name + "发布个人信息成功！");
            return ResultUtil.success();
        } catch (NullPointerException e) {
            log.info("供应商发布个人消息时，企业用户未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }

    }

    @Override
    public Result newServiceProviderEnterpriseInfo(TpEnterpriseProject tpEnterpriseProject, HttpSession session) {
        try {
            String name = session.getAttribute("serviceProviderName").toString();
            String uuid = session.getAttribute("serviceProviderUuid").toString();
            tpEnterpriseProject.setName(name);
            tpEnterpriseProject.setUuid(uuid);
            tpEnterpriseProject.setRegisterTime(new Date());
            tpEnterpriseProject.setServiceProvider(true);
            serviceProviderMapper.newEnterpriseInfo(tpEnterpriseProject);
            serviceProviderMapper.addEnterpriseIconAddress(tpEnterpriseProject);
            log.info("企业用户:" + name + "发布个人信息成功！");
            return ResultUtil.success();
        } catch (NullPointerException e) {
            log.info("发布企业消息时，用户未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }

    }

   @Override
    public Result delPersonPro(Integer id, HttpSession session){
        TpPersonInfo tpPersonInfo = personalMapper.selectPersonInfoById(id);
        if (tpPersonInfo == null) {
            return ResultUtil.error(ResultEnum.DEL_ERROR);
        }
        String uuid = session.getAttribute("serviceProviderUuid").toString();
        String name = session.getAttribute("serviceProviderName").toString();
        personalMapper.deletePersonInfo(id,uuid);
        log.info("供应商用户:"+name+",删除了一条id为："+id+"的，发布的个人信息");
        return ResultUtil.success();
    }

    @Override
    public Result delEnterprisePro(Integer id, HttpSession session){
        TpEnterpriseProject tpEnterpriseProject = enterpriseMapper.selectProjectById(id);
        if (tpEnterpriseProject == null) {
            return ResultUtil.error(ResultEnum.DEL_ERROR);
        }
        String uuid = session.getAttribute("serviceProviderUuid").toString();
        String name = session.getAttribute("serviceProviderName").toString();
        enterpriseMapper.deleteEnterpriseProject(id,uuid);
        log.info("企业用户:"+name+",删除了一条id为："+id+"的发布信息");
        return ResultUtil.success();
    }
    

    @Override
    public TpServiceProvider selectAllByName(String name) {
        return serviceProviderMapper.selectAllByName(name);
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
        TpServiceProvider tpPersonal = serviceProviderMapper.selectAllByName(serviceProviderResetPass.getName());
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
    public void sendMail(CheckMail checkMail) throws Exception {
        String name = checkMail.getName();
        TpServiceProvider tpServiceProvider = this.selectAllByName(name);
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
        TpServiceProvider tpServiceProvider = this.selectAllByName(name);
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