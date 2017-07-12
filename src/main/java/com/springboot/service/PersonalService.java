package com.springboot.service;

import com.springboot.domain.TpPersonal;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface PersonalService {
    String login(String name, String password);
    TpPersonal selectByName(String name);

    String insertPerson(TpPersonal person);
}


