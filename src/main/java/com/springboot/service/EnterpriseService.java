package com.springboot.service.impl;

import com.springboot.domain.Result;
import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpFile;
import com.springboot.dto.CheckMail;
import com.springboot.dto.EnterpriseProject;
import com.springboot.dto.EnterpriseResetPass;
import com.springboot.dto.Password;
import com.springboot.enums.ResultEnum;
import com.springboot.mapper.EnterpriseMapper;
import com.springboot.service.EnterpriseService;
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
public class EnterpriseServiceImpl implements EnterpriseService {

    @Value("${spring.mail.username}")
    private String username;
    private JavaMailSender javaMailSender;
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    public EnterpriseServiceImpl(EnterpriseMapper enterpriseMapper, JavaMailSender javaMailSender) {
        this.enterpriseMapper = enterpriseMapper;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Result<TpEnterprise> selectEnterpriseByName(HttpSession session) {
        try {
            String name = session.getAttribute("enterpriseUuid").toString();
            TpEnterprise tpEnterprise = enterpriseMapper.selectEnterpriseByName(name);
            return ResultUtil.success(tpEnterprise);
        } catch (Exception e) {
            log.info("未登录"+ e.getMessage());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
    }

    @Override
    public Result updateEnterpriseByName(TpEnterprise tpEnterprise, HttpSession session) {
        try {
            String uuid = session.getAttribute("enterpriseUuid").toString();
            tpEnterprise.setUuid(uuid);
            enterpriseMapper.updateEnterpriseByName(tpEnterprise);
            log.info(uuid + "修改企业资料成功！");
            return ResultUtil.success(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.info("修改企业资料时，用户未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }

    }

    @Override
    public Result newEnterpriseProject(TpEnterpriseProject tpEnterpriseProject, HttpSession session) {
        try {
            String name = session.getAttribute("enterpriseName").toString();
            String uuid = session.getAttribute("enterpriseUuid").toString();
            tpEnterpriseProject.setName(name);
            tpEnterpriseProject.setUuid(uuid);
            tpEnterpriseProject.setRegisterTime(new Date());
            tpEnterpriseProject.setServiceProvider(false);
            enterpriseMapper.newProject(tpEnterpriseProject);
            log.info("企业用户:"+name+"发布个人信息成功！");
            return ResultUtil.success();
        } catch (NullPointerException e) {
            log.info("发布企业消息时，用户未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
    }

    @Override
    public Result updateEnterpriseProjectById(TpEnterpriseProject tpEnterpriseProject, HttpSession session) {
        try {
            String name = session.getAttribute("enterpriseName").toString();
            String uuid = session.getAttribute("enterpriseUuid").toString();
            tpEnterpriseProject.setName(name);
            tpEnterpriseProject.setUuid(uuid);
            tpEnterpriseProject.setRegisterTime(new Date());
            enterpriseMapper.updateEnterpriseProjectById(tpEnterpriseProject);
            log.info("企业用户:"+name+"修改发布信息成功！");
            return ResultUtil.success();
        } catch (NullPointerException e) {
            log.info("企业用户修改发布信息时未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
    }

  @Override
    public Result deleteEnterpriseProject(Integer id,HttpSession session) {
        TpEnterpriseProject tpEnterpriseProject = enterpriseMapper.selectProjectById(id);
        if (tpEnterpriseProject == null) {
            return ResultUtil.error(ResultEnum.DEL_ERROR);
        }
        String uuid = session.getAttribute("enterpriseUuid").toString();
        String name = session.getAttribute("enterpriseName").toString();
        enterpriseMapper.deleteEnterpriseProject(id,uuid);
        log.info("企业用户删除了一条id为："+id+"的发布信息");
        return ResultUtil.success();
    }















    @Override
    public TpEnterprise selectAllByName(String name) {
        return enterpriseMapper.selectAllByName(name);
    }


    public String updateEnterprisePass(Password password, HttpSession session) {
        try {
            password.setName(session.getAttribute("name").toString());
        }catch (NullPointerException e){
            log.info(e.toString());
            return "用户未登录";
        }
        String TPPassword = enterpriseMapper.selectByName(password.getName()).getPassword();
        if (password.getPassword().equals(TPPassword)) {
            if (password.getNewPassword().equals(password.getRetypePassword())) {
                if (TPPassword.equals(password.getNewPassword())) {
                    return "新密码与旧密码相同，请重新输入！";
                } else {
                    password.setPassword(password.getNewPassword());
                    enterpriseMapper.updateEnterprisePass(password);
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
    public String resetEnterprisePass(EnterpriseResetPass enterpriseResetPass) {
        TpEnterprise tpEnterprise = enterpriseMapper.selectAllByName(enterpriseResetPass.getName());
        if (tpEnterprise != null) {
            if (enterpriseResetPass.getTel().equals(tpEnterprise.getTel())) {
                enterpriseMapper.resetPass(enterpriseResetPass);
                return "重置密码成功";
            } else {
                return "电话号码错误，请重试！";
            }
        } else {
            return "没有此用户！";
        }
    }



    @Override
    public List<TpEnterpriseProject> selectProjects(EnterpriseProject enterpriseProject) {
        List<TpEnterpriseProject> tpEnterpriseProjects = enterpriseMapper.selectProjects(enterpriseProject);
        return tpEnterpriseProjects;
    }

    @Override
    public TpEnterpriseProject selectProjectById(EnterpriseProject enterpriseProject) {
        TpEnterpriseProject tpEnterpriseProject = enterpriseMapper.selectProjectById(enterpriseProject.getId());
        int clickAmount = tpEnterpriseProject.getClickAmount() + 1;
        tpEnterpriseProject.setClickAmount(clickAmount);
        String iconAddress;
        TpFile tpFile = enterpriseMapper.selectTpFileByName(tpEnterpriseProject);
        if (tpFile == null) {
            iconAddress = "未上传头像。";
        } else {
            iconAddress = tpFile.getPicturePath();
        }
        tpEnterpriseProject.setIconAddress(iconAddress);
        if (clickAmount < 50) {
            tpEnterpriseProject.setStars(0);
        } else if (50 <= clickAmount && clickAmount < 100) {
            tpEnterpriseProject.setStars(1);
        } else if (100 <= clickAmount && clickAmount < 200) {
            tpEnterpriseProject.setStars(2);
        } else if (200 < clickAmount && clickAmount < 400) {
            tpEnterpriseProject.setStars(3);
        } else if (400 <= clickAmount && clickAmount < 700) {
            tpEnterpriseProject.setStars(4);
        } else if (700 <= clickAmount) {
            tpEnterpriseProject.setStars(5);
        }
        enterpriseMapper.addClickAmount(tpEnterpriseProject);
        return tpEnterpriseProject;
    }

    @Override
    public Result<List<TpEnterpriseProject>> selectEnterpriseInfoLatestAmount(Integer amount){
        List<TpEnterpriseProject> tpEnterpriseProjects = enterpriseMapper.selectEnterpriseInfoLatestAmount(amount);
        log.info("查询了企业发布的最新"+amount+"条信息！");
        return ResultUtil.success(tpEnterpriseProjects);
    }

   @Override
    public void sendMail(CheckMail checkMail) throws Exception {
        String name = checkMail.getName();
        TpEnterprise tpEnterprise = this.selectAllByName(name);
        String activeCode = tpEnterprise.getActiveCode();
        String email = tpEnterprise.getEmail();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(username);
        helper.setTo(email);
        helper.setSubject("主题：点击链接激活账户");
        StringBuffer sb = new StringBuffer();
        sb.append("<h1>请点击以下链接以激活账户：</h1>");
        sb.append("<a href=\"http://localhost:8080/translate/enterprise/emailCheck.action?name=");
        sb.append(name);
        sb.append("&activeCode=");
        sb.append(activeCode);
        sb.append("\">http://localhost:8080/translate/enterprise/emailCheck.action?name=");
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
        TpEnterprise tpEnterprise = this.selectAllByName(name);
        String tpName = tpEnterprise.getName();
        String tpActiveCode = tpEnterprise.getActiveCode();
        Boolean status = tpEnterprise.getStatus();
        if (tpName != null && tpName.equals(name)) {
            if (activeCode.equals(tpActiveCode)) {
                if (status == false) {
                    tpEnterprise.setStatus(true);
                    enterpriseMapper.updateStatus(tpEnterprise);
                    return "账户激活成功！";
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
