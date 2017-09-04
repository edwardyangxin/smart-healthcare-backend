package com.springboot.service.impl;

import com.springboot.domain.Result;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.CheckMail;
import com.springboot.dto.Password;
import com.springboot.dto.ResetPass;
import com.springboot.enums.ResultEnum;
import com.springboot.mapper.EnterpriseMapper;
import com.springboot.mapper.PersonalMapper;
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
    private PersonalMapper personalMapper;
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    public ServiceProviderServiceImpl(ServiceProviderMapper serviceProviderMapper, PersonalMapper personalMapper,
                                      EnterpriseMapper enterpriseMapper, JavaMailSender javaMailSender) {
        this.serviceProviderMapper = serviceProviderMapper;
        this.javaMailSender = javaMailSender;
        this.personalMapper = personalMapper;
        this.enterpriseMapper = enterpriseMapper;
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
        String uuid = session.getAttribute("serviceProviderUuid").toString();
        Integer ids = personalMapper.deletePersonInfo(id, uuid);
        if (ids == null) {
            return ResultUtil.error(ResultEnum.DEL_ERROR);
        }
        String name = session.getAttribute("serviceProviderName").toString();
        personalMapper.deletePersonInfo(id, uuid);
        log.info("供应商用户:" + name + ",删除了一条id为：" + id + "的，发布的个人信息");
        return ResultUtil.success();
    }

    @Override
    public Result delEnterprisePro(Integer id, HttpSession session) {
        TpEnterpriseProject tpEnterpriseProject = enterpriseMapper.selectProjectById(id);
        if (tpEnterpriseProject == null) {
            return ResultUtil.error(ResultEnum.DEL_ERROR);
        }
        String uuid = session.getAttribute("serviceProviderUuid").toString();
        String name = session.getAttribute("serviceProviderName").toString();
        enterpriseMapper.deleteEnterpriseProject(id, uuid);
        log.info("企业用户:" + name + ",删除了一条id为：" + id + "的发布信息");
        return ResultUtil.success();
    }


    @Override
    public Result updateServicePersonPro(TpPersonInfo tpPersonInfo, HttpSession session) {
        String name = session.getAttribute("serviceProviderName").toString();
        String uuid = session.getAttribute("serviceProviderUuid").toString();
        tpPersonInfo.setName(name);
        tpPersonInfo.setUuid(uuid);
        tpPersonInfo.setRegisterTime(new Date());
        personalMapper.updateInfoById(tpPersonInfo);
        log.info("供应商:" +name + "修改个人发布信息成功！");
        return ResultUtil.success(ResultEnum.UPDATE_SUCCESS);
    }

    @Override
    public Result updateServiceEnterprisePro(TpEnterpriseProject tpEnterpriseProject, HttpSession session) {
        String name = session.getAttribute("serviceProviderName").toString();
        String uuid = session.getAttribute("serviceProviderUuid").toString();
        tpEnterpriseProject.setName(name);
        tpEnterpriseProject.setUuid(uuid);
        tpEnterpriseProject.setRegisterTime(new Date());
        enterpriseMapper.updateEnterpriseProjectById(tpEnterpriseProject);
        log.info("供应商:" +name + "修改企业发布信息成功！");
        return ResultUtil.success(ResultEnum.UPDATE_SUCCESS);
    }


    @Override
    public Result updateServiceProviderPass(Password password, HttpSession session) {
        String uuid = session.getAttribute("enterpriseUuid").toString();
        String passwordReturn = serviceProviderMapper.selectByName(uuid).getPassword();
        if (!password.getPassword().equals(passwordReturn)) {
            log.info("旧密码不正确！");
            return ResultUtil.error(ResultEnum.OLDPASSWORD_ERROR);
        }
        if (!password.getNewPassword().equals(password.getRetypePassword())) {
            log.info("两次输入的新密码不一致！请重新输入！");
            return ResultUtil.error(ResultEnum.DIFPASSWORD_ERROR);
        }
        if (passwordReturn.equals(password.getNewPassword())) {
            log.info("新旧密码相同，请重新输入新密码！");
            return ResultUtil.error(ResultEnum.PASSWORDREPEAT_ERROR);
        }
        password.setPassword(password.getNewPassword());
        serviceProviderMapper.updateServiceProviderPass(password);
        return ResultUtil.success(ResultEnum.PASSRESET_SUCCESS);

    }
    @Override
    public TpServiceProvider selectAllByName(String name) {
        return serviceProviderMapper.selectAllByName(name);
    }
    @Override
    public Result resetServiceProviderPass(ResetPass resetPass) {
        TpServiceProvider tpServiceProvider = serviceProviderMapper.selectAllByName(resetPass.getName());
        String name = resetPass.getName();
        if (tpServiceProvider == null) {
            log.info("重置供应商密码时，无此用户！"+resetPass.getName());
            return ResultUtil.error(ResultEnum.NOT_EXIST_ERROR);
        }
        if (!resetPass.getEmail().equals(tpServiceProvider.getEmail())) {
            log.info(name+"重置供应商密码时，邮箱输入不正确！错误邮箱："+resetPass.getEmail());
            return ResultUtil.error(ResultEnum.Repeat_eamil_Error);
        }
        if (!resetPass.getTel().equals(tpServiceProvider.getTel())) {
            log.info(name+"重置供应商密码时,手机号输入不正确！错误手机号："+resetPass.getTel());
            return ResultUtil.error(ResultEnum.Repeat_tel_Error);
        }
        resetPass.setUuid(tpServiceProvider.getUuid());
        serviceProviderMapper.resetPass(resetPass);
        log.info("供应商用户：" +name+ "重置密码成功！");
        return ResultUtil.success(ResultEnum.PASSSFIND_SUCCESS);
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