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
    String login(Login login, HttpSession session);

    TpPersonal selectByName(String name);

    String insertPerson(TpPersonal tpPersonal,TpFile tpFile);

    String updatePersonalPass(Password password, HttpSession session);

    String resetPersonalPass(PersonalResetPass personalResetPass);

    String updatePersonByName(Personal person, HttpSession session);

    Result<TpPersonInfo> newInfo(TpPersonInfo tpPersonInfo, HttpSession session);

    void updateInfo(TpPersonInfo tpPersonInfo);

    String delInfo(PersonInfo personInfo);

    List<TpPersonInfo> selectInfos(PersonInfo personInfo);

    TpPersonInfo selectInfoById(PersonInfo personInfo);

    List<TpPersonInfo> selectLatest(Integer amount);

    void sendMail(CheckMail checkMail) throws Exception;

    String emailCheck(CheckMail checkMail);
}


