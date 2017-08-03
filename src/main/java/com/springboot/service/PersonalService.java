package com.springboot.service;

import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.*;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface PersonalService {
    String login(Login login);

    TpPersonal selectByName(String name);

    String insertPerson(TpPersonal person);

    String updatePersonalPass(Password password);

    String resetPersonalPass(PersonalResetPass personalResetPass);

    String updatePersonByName(Personal person);

    String newInfo(TpPersonInfo tpPersonInfo);

    List<TpPersonInfo> selectInfos(SelectPersonInfo selectPersonInfo);

    List<TpPersonInfo> selectLatest();
}


