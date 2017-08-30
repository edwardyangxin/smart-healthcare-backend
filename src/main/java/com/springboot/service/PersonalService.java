package com.springboot.service;

import com.springboot.domain.Result;
import com.springboot.domain.TpFile;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface PersonalService {

    ////根据session中的name，去查询个人用户自身信息
    Result<TpPersonal> selectPersonaByName(HttpSession session);

    Result<TpPersonal> insertPerson(TpPersonal tpPersonal, TpFile tpFile);

    Result<Password> updatePersonalPass(Password password, HttpSession session);

    String resetPersonalPass(PersonalResetPass personalResetPass);

    Result<TpPersonal> updatePersonByName(TpPersonal tpPersonal, HttpSession session);

    Result<TpPersonInfo> newInfo(TpPersonInfo tpPersonInfo, HttpSession session);

    Result<TpPersonInfo> updateInfo(TpPersonInfo tpPersonInfo);

    Result<PersonInfo> delInfo(PersonInfo personInfo);

    Result<TpPersonInfo> selectInfos(PersonInfo personInfo);

    Result<TpPersonInfo> selectInfoById(PersonInfo personInfo);

    //根据amount的值查询个人最新发布的几条信息
    Result<List<TpPersonInfo>> selectPersonInfoLatestAmount(Integer amount);

    void sendMail(CheckMail checkMail) throws Exception;

    String emailCheck(CheckMail checkMail);
}


