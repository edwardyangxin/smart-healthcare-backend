package com.springboot.service;

import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpPersonal;
import com.springboot.domain.TpServiceProvider;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface IndexService {

    void insertPerson(TpPersonal person);
    void insertEnterprise(TpEnterprise enterprise);
    void insertServiceProvider(TpServiceProvider serviceProvider);
}
