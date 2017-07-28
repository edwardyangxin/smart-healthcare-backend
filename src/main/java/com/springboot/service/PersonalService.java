package com.springboot.service;

import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.Password;
import com.springboot.dto.Personal;
import com.springboot.dto.PersonalResetPass;
import com.springboot.dto.SelectPersonInfo;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface PersonalService {
    String login(String name, String password);

    TpPersonal selectByName(String name);

    String insertPerson(TpPersonal person);

    String updatePersonalPass(Password password);

    String resetPersonalPass(PersonalResetPass personalResetPass);

    String updatePersonByName(Personal person);

    String newInfo(TpPersonInfo tpPersonInfo);

    List<TpPersonInfo> selectInfoByName(String name);

    List<TpPersonInfo> selectInfos(SelectPersonInfo selectPersonInfo);
}


