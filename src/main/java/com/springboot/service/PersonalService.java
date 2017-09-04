package com.springboot.service;

import com.springboot.domain.Result;
import com.springboot.domain.TpFile;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.CheckMail;
import com.springboot.dto.Password;
import com.springboot.dto.PersonInfo;
import com.springboot.dto.ResetPass;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface PersonalService {

    //根据session中的UUID，去查询个人用户自身信息
    Result<TpPersonal> selectPersonaByName(HttpSession session);

    //完善修改个人信息
    Result  updatePersonByName(TpPersonal tpPersonal, HttpSession session);

    //发布个人信息
    Result newPersonInfo(TpPersonInfo tpPersonInfo, HttpSession session);

    //修改个人发布信息
    Result updatPersonInfo(TpPersonInfo tpPersonInfo,HttpSession session);

    //删除个人发布信息
    Result<PersonInfo> deletePersonInfo(Integer id, HttpSession session);

    //修改个人用户密码
    Result updatePersonalPass(Password password, HttpSession session);

    //重置个人密码
    Result resetPersonalPass(ResetPass resetPass);

    Result<TpPersonal> insertPerson(TpPersonal tpPersonal, TpFile tpFile);




    Result<TpPersonInfo> selectInfos(PersonInfo personInfo);

    Result<TpPersonInfo> selectInfoById(PersonInfo personInfo);

    //根据amount的值查询个人最新发布的几条信息
    Result<List<TpPersonInfo>> selectPersonInfoLatestAmount(Integer amount);

    void sendMail(CheckMail checkMail) throws Exception;

    String emailCheck(CheckMail checkMail);
}


