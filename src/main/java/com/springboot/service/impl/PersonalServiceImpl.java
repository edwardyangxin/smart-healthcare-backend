package com.springboot.service.impl;


import com.springboot.domain.Result;
import com.springboot.domain.TpFile;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.*;
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
            String uuid = session.getAttribute("personUuid").toString();
            TpPersonal tpPersonal = personalMapper.selectPersonaByName(uuid);
            return ResultUtil.success(tpPersonal);
        } catch (Exception e) {
            log.info("未登录" + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
    }

    @Override
    public Result updatePersonByName(TpPersonal tpPersonal, HttpSession session) {
        try {
            String uuid = session.getAttribute("personUuid").toString();
            tpPersonal.setUuid(uuid);
            personalMapper.updatePersonByName(tpPersonal);
            log.info(uuid + "更改个人资料成功！");
            return ResultUtil.success(ResultEnum.UPDATE_SUCCESS);
        } catch (NullPointerException e) {
            log.info("更改个人资料时，用户未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
    }

    @Override
    public Result newPersonInfo(TpPersonInfo tpPersonInfo, HttpSession session) {
        try {
            String name = session.getAttribute("personName").toString();
            String uuid = session.getAttribute("personUuid").toString();
            tpPersonInfo.setName(name);
            tpPersonInfo.setUuid(uuid);
            tpPersonInfo.setRegisterTime(new Date());
            tpPersonInfo.setServiceProvider(false);
            personalMapper.newInfo(tpPersonInfo);
            personalMapper.addIconAddress(tpPersonInfo);
            log.info("个人用户:"+name+"发布个人信息成功！");
            return ResultUtil.success();
        } catch (NullPointerException e) {
            log.info("发布个人消息时，用户未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }

    }


    @Override
    public Result updatPersonInfo(TpPersonInfo tpPersonInfo,HttpSession session) {
        try {
            String name = session.getAttribute("personName").toString();
            String uuid = session.getAttribute("personUuid").toString();
            tpPersonInfo.setName(name);
            tpPersonInfo.setUuid(uuid);
            tpPersonInfo.setRegisterTime(new Date());
            tpPersonInfo.setServiceProvider(false);
            personalMapper.updateInfoById(tpPersonInfo);
            log.info("个人用户:"+name+"修改发布信息成功！");
            return ResultUtil.success(ResultEnum.UPDATE_SUCCESS);
        } catch (NullPointerException e) {
            log.info("修改个人发布信息时，用户未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
    }

  @Override
    public Result deletePersonInfo(Integer id,HttpSession session) {
        TpPersonInfo tpPersonInfo = personalMapper.selectInfoById(id);
        if (tpPersonInfo == null) {
            return ResultUtil.error(ResultEnum.DEL_ERROR);
        }
        String uuid = session.getAttribute("personUuid").toString();
        String name = session.getAttribute("personName").toString();
        personalMapper.deletePersonInfo(id,uuid);
        log.info("个人用户"+name+",删除了一条id为："+id+"的发布信息");
        return ResultUtil.success();
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
    public Result updatePersonalPass(Password password, HttpSession session) {
        String uuid = session.getAttribute("enterpriseUuid").toString();
        String passwordReturn = personalMapper.selectByName(uuid).getPassword();
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
        personalMapper.updatePassword(password);
        return ResultUtil.success(ResultEnum.PASSRESET_SUCCESS);
    }

    @Override
    public Result resetPersonalPass(ResetPass resetPass) {
            TpPersonal tpPersonal = personalMapper.selectAllByName(resetPass.getName());
            String name = resetPass.getName();
            if (tpPersonal == null) {
                log.info("重置个人密码时，无此用户！"+resetPass.getName());
                return ResultUtil.error(ResultEnum.NOT_EXIST_ERROR);
            }
            if (!resetPass.getEmail().equals(tpPersonal.getEmail())) {
                log.info(name+"重置个人密码时，邮箱输入不正确！错误邮箱："+resetPass.getEmail());
                return ResultUtil.error(ResultEnum.Repeat_eamil_Error);
            }
            if (!resetPass.getTel().equals(tpPersonal.getTel())) {
                log.info(name+"重置个人密码时,手机号输入不正确！错误手机号："+resetPass.getTel());
                return ResultUtil.error(ResultEnum.Repeat_tel_Error);
            }
            resetPass.setUuid(tpPersonal.getUuid());
            personalMapper.resetPass(resetPass);
            log.info("个人用户：" +name+ "重置密码成功！");
            return ResultUtil.success(ResultEnum.PASSSFIND_SUCCESS);
    }

    @Override
    public Result<List<SelectReturn>> selectPersonAllPro(HttpSession session) {
        String uuid = session.getAttribute("personUuid").toString();
        List<SelectReturn> selectReturns = personalMapper.selectPersonAllPro(uuid);
        return ResultUtil.success(selectReturns);
    }


    @Override
    public Result<TpPersonInfo> selectInfos(PersonInfo personInfo) {
        List<TpPersonInfo> tpPersonInfos = personalMapper.selectInfos(personInfo);
        return ResultUtil.success(tpPersonInfos);
    }

    @Override
    public Result<TpPersonInfo> selectInfoById(PersonInfo personInfo) {
        TpPersonInfo tpPersonInfo = personalMapper.selectInfoById(personInfo.getId());
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


