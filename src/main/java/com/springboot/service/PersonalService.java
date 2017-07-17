package com.springboot.service;

import com.springboot.domain.TpPersonal;
import com.springboot.dto.Password;
import com.springboot.dto.Personal;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface PersonalService {
    String login(String name, String password);

    TpPersonal selectByName(String name);
    String insertPerson(TpPersonal person);

    String updatePersonalPass(Password password);
    String updatePersonByName(Personal person);
}


