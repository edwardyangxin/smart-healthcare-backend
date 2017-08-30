package com.springboot.service.impl;


import com.springboot.domain.Result;
import com.springboot.domain.TpFile;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.CheckMail;
import com.springboot.dto.Password;
import com.springboot.dto.PersonInfo;
import com.springboot.dto.PersonalResetPass;
import com.springboot.enums.ResultEnum;
import com.springboot.mapper.PersonalMapper;
import com.springboot.service.PersonalService;
import com.springboot.tools.ResultUtil;
import com.springboot.tools.UUIDTool;
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
    public Result<TpPersonal> selectPersonaByName(HttpSession session) {
        try {
            String name = session.getAttribute("personName").toString();
            TpPersonal tpPersonal = personalMapper.selectPersonaByName(name);
            return ResultUtil.success(tpPersonal);
        } catch (Exception e) {
            log.info(e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
    }

    public TpPersonal selectByName(String name) {
        return personalMapper.selectAllByName(name);
    }

    @Override
    public Result<TpPersonal> insertPerson(TpPersonal tpPersonal, TpFile tpFile) {
        TpPersonal tpPersonal1 = personalMapper.selectAllByName(tpPersonal.getName());
        tpPersonal.setActiveCode(UUIDTool.getUuid());
        tpPersonal.setStatus(false);
        tpFile.setName(tpPersonal.getName());
        tpFile.setUuid(tpPersonal.getActiveCode());
        tpFile.setPicturePath("http://localhost:8080/translate/pictures/default.jpg");
        if (tpPersonal1 == null) {
            personalMapper.insertPerson(tpPersonal);
            personalMapper.newTpFile(tpFile);
            return ResultUtil.success(ResultEnum.REGISTER_SUCCESS);
        } else {
            return ResultUtil.error(ResultEnum.EXIST_ERROR);
        }
    }

    @Override
    public Result<Password> updatePersonalPass(Password password, HttpSession session) {
        try {
            password.setName(session.getAttribute("name").toString());
        } catch (NullPointerException e) {
            log.info("修改密码用户未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
        String TPPassword = personalMapper.selectByName(password.getName()).getPassword();
        if (password.getPassword().equals(TPPassword)) {
            if (password.getNewPassword().equals(password.getRetypePassword())) {
                if (TPPassword.equals(password.getNewPassword())) {
                    return ResultUtil.error(ResultEnum.PASSWORDREPEAT_ERROR);
                } else {
                    password.setPassword(password.getNewPassword());
                    personalMapper.updatePassword(password);
                    session.removeAttribute("name");
                    return ResultUtil.success(ResultEnum.PASSRESET_SUCCESS);
                }
            } else {
                return ResultUtil.error(ResultEnum.DIFPASSWORD_ERROR);
            }
        } else {
            return ResultUtil.error(ResultEnum.OLDPASSWORD_ERROR);
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
    public Result<TpPersonal> updatePersonByName(TpPersonal tpPersonal, HttpSession session) {
        try {
            tpPersonal.setName(session.getAttribute("name").toString());
        } catch (NullPointerException e) {
            log.info("更改个人资料用户未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
        personalMapper.updatePersonByName(tpPersonal);
        return ResultUtil.success(ResultEnum.UPDATE_SUCCESS);
    }

    @Override
    public Result<TpPersonInfo> newInfo(TpPersonInfo tpPersonInfo, HttpSession session) {
        try {
            tpPersonInfo.setName(session.getAttribute("name").toString());
            tpPersonInfo.setUuid(session.getAttribute("uuid").toString());
        } catch (NullPointerException e) {
            log.info("个人发布消息用户未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
        tpPersonInfo.setRegisterTime(new Date());
        personalMapper.newInfo(tpPersonInfo);
        personalMapper.addIconAddress(tpPersonInfo);
        return ResultUtil.success();
    }

    @Override
    public Result<TpPersonInfo> updateInfo(TpPersonInfo tpPersonInfo) {
        tpPersonInfo.setRegisterTime(new Date());
        personalMapper.updateInfoById(tpPersonInfo);
        return ResultUtil.success(ResultEnum.UPDATE_SUCCESS);
    }

    @Override
    public Result<PersonInfo> delInfo(PersonInfo personInfo) {
        TpPersonInfo tpPersonInfo = personalMapper.selectInfoById(personInfo);
        if (tpPersonInfo != null) {
            personalMapper.delInfo(personInfo.getId());
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.DEL_ERROR);
        }
    }

    @Override
    public Result<TpPersonInfo> selectInfos(PersonInfo personInfo) {
        List<TpPersonInfo> tpPersonInfos = personalMapper.selectInfos(personInfo);
        return ResultUtil.success(tpPersonInfos);
    }

    @Override
    public Result<TpPersonInfo> selectInfoById(PersonInfo personInfo) {
        TpPersonInfo tpPersonInfo = personalMapper.selectInfoById(personInfo);
        int clickAmount = tpPersonInfo.getClickAmount() + 1;
        tpPersonInfo.setClickAmount(clickAmount);
        String iconAddress;
        TpFile tpFile = personalMapper.selectTpFileByName(tpPersonInfo);
        if (tpFile == null) {
            iconAddress = "未上传头像。";
        } else {
            iconAddress = tpFile.getPicturePath();
        }
        tpPersonInfo.setIconAddress(iconAddress);
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
        return ResultUtil.success(tpPersonInfo);
    }

    @Override
    public Result<List<TpPersonInfo>> selectPersonInfoLatestAmount(Integer amount) {
        List<TpPersonInfo> tpPersonInfos = personalMapper.selectPersonInfoLatestAmount(amount);
        log.info("查询了个人发布的最新" + amount + "条信息！");
        return ResultUtil.success(tpPersonInfos);
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


